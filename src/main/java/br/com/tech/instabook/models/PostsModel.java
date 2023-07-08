package br.com.tech.instabook.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Optional;

@AllArgsConstructor @NoArgsConstructor @Getter @Setter @Entity @Table(name = "post")
public class PostsModel {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;
    private Boolean deleted = false;
    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDate createdAt = LocalDate.now();
    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDate updatedAt;
    @Column(columnDefinition = "VARCHAR(50)", nullable = false) @NotEmpty(message = "Deve haver um ´titulo!")
    private String title;
    @Column(columnDefinition = "VARCHAR(200)", nullable = false) @NotEmpty(message = "Descricao nao pode estar vazia!")
    private String description;
    private String photoLink;
    private String videoLink;
    @NotNull
    private Boolean isPrivate;
    @ManyToOne @JoinColumn(name = "profile_id", nullable = false) @NotEmpty(message = "Publicacao precisa de um dono!")
    private ProfileModel user;

    public boolean changePrivacity() {
        this.setIsPrivate(!this.getIsPrivate());
        return this.getIsPrivate();
    }

    public boolean logicalDeleted() {
        this.setDeleted(!this.getDeleted());
        return this.getDeleted();
    }

    public void setUser(Optional<ProfileModel> profileModel) {
        this.user = profileModel.get();
    }
}
