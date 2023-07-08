package br.com.tech.instabook.controllers;

import br.com.tech.instabook.dtos.PostsRecordDTO;
import br.com.tech.instabook.dtos.PostsResponseDTO;
import br.com.tech.instabook.models.PostsModel;
import br.com.tech.instabook.services.PostsService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/instabook/posts")
public class PostsController {
    @Autowired
    private PostsService postsService;
    @PostMapping("/create-post")
    public ResponseEntity<Object> createPost(@RequestBody @Valid PostsRecordDTO postsRecordDTO, Long profileId) {
        var postsModel = new PostsModel();
        BeanUtils.copyProperties(postsRecordDTO, postsModel);
        postsService.create(postsModel, profileId);
        return ResponseEntity.status(HttpStatus.CREATED).body(new PostsResponseDTO(postsModel));
    }
    @PutMapping("/editing/status-post")
    public ResponseEntity<Object> switchPostPrivacity(@RequestBody @Valid PostsRecordDTO postsRecordDTO) {
        var postsModel = new PostsModel();
        BeanUtils.copyProperties(postsRecordDTO, postsModel);
        var response = postsService.updatePostPrivacity(postsModel);
        return ResponseEntity.status(HttpStatus.OK).body(new PostsResponseDTO(response));
    }
    @GetMapping("/{username}")
    public ResponseEntity<Object> findPostsByUser(@PathVariable @RequestParam String username) {
        var lista = postsService.findPostsByProfile(username).stream().map(PostsResponseDTO::new);
        return ResponseEntity.status(HttpStatus.OK).body(lista);
    }
    @DeleteMapping("/delete-post/{id}") @Secured("ROLE_ADMIN")
    public ResponseEntity<Object> deletePost(@PathVariable Long id) {
        return ResponseEntity.ok(postsService.deletePost(id));
    }
    @PutMapping("/editing/post-content")
    public ResponseEntity<Object> editPost(PostsModel postsModel) {
        var response = postsService.editarPost(postsModel);
        return ResponseEntity.status(HttpStatus.OK).body(new PostsResponseDTO(response));
    }
}
