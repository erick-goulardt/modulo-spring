package br.com.tech.instabook.controllers;

import br.com.tech.instabook.dtos.AuthenticationDTO;
import br.com.tech.instabook.dtos.LoginResponseDTO;
import br.com.tech.instabook.dtos.ProfileRecordDTO;
import br.com.tech.instabook.dtos.ProfileResponseDTO;
import br.com.tech.instabook.exceptions.UserNotFoundException;
import br.com.tech.instabook.models.ProfileModel;
import br.com.tech.instabook.services.ProfileService;

import br.com.tech.instabook.util.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/instabook")
public class ProfileController {
    @Autowired
    public ProfileService profileService;
    @Autowired
    public AuthenticationManager manager;
    @Autowired
    public TokenService tokenService;

    @PostMapping("/register")
    public ResponseEntity<Object> saveProfile(@RequestBody @Valid ProfileRecordDTO profileRecordDTO) {
        if(profileService.existsByEmail(profileRecordDTO.email())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email já está em uso!");
        } else if (profileService.existsByPhone(profileRecordDTO.phone())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Telefone já cadastrado!");
        } else if (profileService.existsByUsername(profileRecordDTO.username())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username não disponível!");
        } else {
            var profileModel = new ProfileModel();
            BeanUtils.copyProperties(profileRecordDTO, profileModel);
            profileModel.setPassword(new BCryptPasswordEncoder().encode(profileModel.getPassword()));
            profileService.save(profileModel);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ProfileResponseDTO(profileModel));
        }
    }
    @PostMapping("/login")
    public ResponseEntity loginUser(@RequestBody @Valid AuthenticationDTO userData) {
        var userCredentials = new UsernamePasswordAuthenticationToken(userData.username(), userData.password());
        var authentication = this.manager.authenticate(userCredentials);
        var token = tokenService.generateToken((ProfileModel)authentication.getPrincipal());
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @GetMapping("/profiles")
    public ResponseEntity<Object> findAllProfiles() {
        var retorno = profileService.findAll().stream().map(ProfileResponseDTO::new);
        return ResponseEntity.status(HttpStatus.FOUND).body(retorno);
    }

    @GetMapping(value = "/{username}")
    public ResponseEntity<Optional> findProfile(@PathVariable String username) throws UserNotFoundException {
        var retorno = profileService.findProfileByUsername(username).map(ProfileResponseDTO::new);
        return ResponseEntity.status(HttpStatus.FOUND).body(retorno);
    }

    @DeleteMapping(value = "/delete-profile/{id}") @Secured("ROLE_ADMIN")
    public ResponseEntity<Optional> deleteProfile(@PathVariable("id") Long id) {
        return ResponseEntity.ok(profileService.deleteProfile(id));
    }

    @PutMapping(value = "/editing") @Secured("ROLE_ADMIN")
    public ResponseEntity<Object> editProfile(@RequestBody @Valid ProfileModel profileModel) throws UserNotFoundException {
        var retorno = profileService.updateProfile(profileModel);
        return ResponseEntity.status(HttpStatus.OK).body(new ProfileResponseDTO(retorno));
    }
    @GetMapping("/profiles-non-deleted")
        public ResponseEntity<Object> findAllActiveProfile() {
        var retorno = profileService.findAllNotDeleted().stream().map(ProfileResponseDTO::new);
            return ResponseEntity.status(HttpStatus.OK).body(retorno);
    }
    @GetMapping("/profiles-deleted")
    public ResponseEntity<Object> findAllNotActiveProfile() {
        var retorno = profileService.findAllDeleted().stream().map(ProfileResponseDTO::new);
        return ResponseEntity.status(HttpStatus.OK).body(retorno);
    }
    @PutMapping("/editing/disable-account")
    public ResponseEntity<Object> switchAccountStatus(@RequestBody @Valid ProfileRecordDTO profileRecordDTO) {
        var profileModel = new ProfileModel();
        BeanUtils.copyProperties(profileRecordDTO, profileModel);
        var retorno = profileService.updateAccountStatus(profileModel);
        return ResponseEntity.status(HttpStatus.OK).body(new ProfileResponseDTO(retorno));
    }

}


