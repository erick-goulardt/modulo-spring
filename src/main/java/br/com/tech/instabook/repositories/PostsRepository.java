package br.com.tech.instabook.repositories;

import br.com.tech.instabook.models.PostsModel;

import br.com.tech.instabook.models.ProfileModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface PostsRepository extends JpaRepository<PostsModel, Long> {

    List<PostsModel> findAllByUserAndDeletedIsFalse(ProfileModel profileModel);

    public List<PostsModel> findAll();
    public Optional<PostsModel> deletePostsModelById(Long id);
    public PostsModel findByIdAndIsPrivateFalse(Long id);
    public PostsModel findByIdAndIsPrivateTrue(Long id);
    public List<PostsModel> findAllByIsPrivateFalse();
    public Optional<PostsModel> findById(Long id);
    public List<PostsModel> findAllByIsPrivateTrue();
    List<PostsModel> findAllByUser_Username(String username);
    Optional<PostsModel> findPostsModelById(Long id);
}
