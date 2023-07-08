package br.com.tech.instabook.dtos;

import br.com.tech.instabook.models.ProfileModel;
import jakarta.validation.constraints.NotBlank;

public record AuthenticationDTO(@NotBlank String username, @NotBlank String password) {
    public AuthenticationDTO(ProfileModel profileModel) {
        this(profileModel.getUsername(), profileModel.getPassword());
    }
}
