package br.com.tech.instabook.dtos;

import br.com.tech.instabook.util.ProfileRole;
import jakarta.validation.constraints.NotBlank;


public record ProfileRecordDTO(@NotBlank String username, @NotBlank String name, @NotBlank String password, @NotBlank String email, @NotBlank String phone, ProfileRole role) {

}
