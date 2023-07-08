package br.com.tech.instabook.dtos;

import br.com.tech.instabook.models.PostsModel;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record PostsResponseDTO(@NotBlank String title,@NotBlank String description,@NotBlank LocalDate createdAt, LocalDate updatedAt, Boolean isPrivate) {
    public PostsResponseDTO(PostsModel postsModel) {
        this(postsModel.getTitle(), postsModel.getDescription(), postsModel.getCreatedAt(), postsModel.getUpdatedAt(), postsModel.getIsPrivate());
    }
}
