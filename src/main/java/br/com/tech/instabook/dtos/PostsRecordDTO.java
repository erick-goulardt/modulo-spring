package br.com.tech.instabook.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record PostsRecordDTO(LocalDate updatedAt, @NotBlank String title, @NotBlank String description, String photolink, String videolink, @NotNull Boolean isPrivate,@NotNull LocalDate createdAt) {

}
