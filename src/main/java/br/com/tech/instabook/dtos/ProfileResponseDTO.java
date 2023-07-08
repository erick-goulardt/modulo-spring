package br.com.tech.instabook.dtos;

import br.com.tech.instabook.models.ProfileModel;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record ProfileResponseDTO(@NotBlank String username, @NotBlank String name, @NotBlank String email, @NotBlank String phone, Long id, @NotBlank LocalDate createdAt) {

    public ProfileResponseDTO(ProfileModel profileModel) {
        this(profileModel.getUsername(), profileModel.getName(), profileModel.getEmail(), profileModel.getPhone(), profileModel.getId(), profileModel.getCreatedAt());
    }
}
