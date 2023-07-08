package br.com.tech.instabook.services;

import br.com.tech.instabook.models.PostsModel;
import br.com.tech.instabook.models.ProfileModel;
import br.com.tech.instabook.repositories.PostsRepository;
import br.com.tech.instabook.repositories.ProfileRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static br.com.tech.instabook.util.VerifierInputs.isUpdatablePost;

@Service
public class PostsService {
    @Autowired
    private PostsRepository postsRepository;
    @Autowired
    private ProfileRepository profileRepository;
    @Transactional
    public PostsModel create(@Valid PostsModel postsModel, Long profileId) {
        Optional<ProfileModel> profileModel = profileRepository.findById(profileId);
            postsModel.setUser(profileModel);
           return postsRepository.save(postsModel);
    }
    @Transactional
    public Optional<PostsModel> deletePost(Long id) {
        return postsRepository.deletePostsModelById(id);
    }
    public PostsModel updatePostPrivacity(@Valid PostsModel postsModel) {
        Optional<PostsModel> postOptional = postsRepository.findById(postsModel.getId());
        PostsModel post = postOptional.get();
        post.setUpdatedAt(LocalDate.now());
        post.changePrivacity();
        post = postsRepository.save(post);
        return post;
    }
    public List<PostsModel> findPostsByProfile(String username) {
        UserDetails profileModel = profileRepository.findByUsername(username);
        return postsRepository.findAllByUser_Username(profileModel.getUsername());
    }

    public PostsModel editarPost(PostsModel postAtualizado) {
        PostsModel postExistente = postsRepository.findById(postAtualizado.getId())
                .orElseThrow(() -> new IllegalArgumentException("Post nao encontrado ID: " + postAtualizado.getId()));
        if(isUpdatablePost(postAtualizado)) {
            postExistente.setTitle(postAtualizado.getTitle());
            postExistente.setDescription(postAtualizado.getDescription());
            postExistente.setVideoLink(postAtualizado.getVideoLink());
            postExistente.setPhotoLink(postAtualizado.getPhotoLink());
            postExistente.setUpdatedAt(LocalDate.now());
        } else {
            throw new NullPointerException("Não vou validado corretamente os campos do post!");
        }
        postsRepository.save(postExistente);
        return postExistente;
    }

}
