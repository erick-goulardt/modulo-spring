package br.com.tech.instabook.services;

import br.com.tech.instabook.exceptions.UserNotFoundException;
import br.com.tech.instabook.models.ProfileModel;
import br.com.tech.instabook.repositories.ProfileRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import static br.com.tech.instabook.util.VerifierInputs.isUpdatableProfile;

@Service
public class ProfileService implements UserDetailsService {
    @Autowired
    private ProfileRepository profileRepository;
    public ProfileModel updateProfile(@Valid ProfileModel profileModel) throws UserNotFoundException {
        Optional<ProfileModel> profileOptional = profileRepository.findProfileModelById(profileModel.getId());
        if(profileOptional.isPresent()) {
            if(isUpdatableProfile(profileModel)) {
                ProfileModel profile = profileOptional.get();
                profile.setUsername(profileModel.getUsername());
                profile.setPassword(profileModel.getPassword());
                profile.setName(profileModel.getName());
                profile.setEmail(profileModel.getEmail());
                profile.setPhone(profileModel.getPhone());
                profile.setUpdatedAt(LocalDate.now());
                profile = profileRepository.save(profile);
                return profile;
            }
        }
        throw new UserNotFoundException();
    }
    @Transactional
    public ProfileModel save(@Valid ProfileModel profileModel) {
        profileModel.setProfileLink(profileModel.getProfileLink() + profileModel.getUsername());
        return profileRepository.save(profileModel);
    }
    public boolean existsByEmail(String email) {
        return profileRepository.existsByEmail(email);
    }
    public List<ProfileModel> findAll() {
        return profileRepository.findAll();
    }
    @Transactional
    public Optional<ProfileModel> deleteProfile(Long id) {
        return profileRepository.deleteProfileModelById(id);
    }
    public List<ProfileModel> findAllNotDeleted() {
        return profileRepository.findProfileModelByDeletedIsFalse();
    }
    public List<ProfileModel> findAllDeleted() {
        return profileRepository.findProfileModelByDeletedIsTrue();
    }
    public boolean existsByPhone(String phone) {
        return profileRepository.existsByPhone(phone);
    }
    public boolean existsByUsername(String username) {
        return profileRepository.existsByUsername(username);
    }
    public Optional<ProfileModel> findProfileByUsername(String username) throws UserNotFoundException {
        return Optional.ofNullable(profileRepository.findByUsernameContainingIgnoreCase(username).orElseThrow(UserNotFoundException::new));
    }
    public ProfileModel updateAccountStatus(@Valid ProfileModel profileModel) {
        Optional<ProfileModel> profileOptional = profileRepository.findProfileModelById(profileModel.getId());
        ProfileModel profile = profileOptional.get();
        profile.setUpdatedAt(LocalDate.now());
        profile.changeActivity();
        profile = profileRepository.save(profile);
        return profile;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return profileRepository.findByUsername(username);
    }
}

