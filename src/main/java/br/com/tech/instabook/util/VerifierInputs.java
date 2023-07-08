package br.com.tech.instabook.util;

import br.com.tech.instabook.models.PostsModel;
import br.com.tech.instabook.models.ProfileModel;
public class VerifierInputs {
    public static boolean isUpdatableProfile(ProfileModel profileModel) {
        if(profileModel.getDeleted() == false) {
            if(profileModel.getName() != null && ! profileModel.getName().isBlank()) {
            } else return false;
            if(profileModel.getEmail() != null && ! profileModel.getEmail().isBlank() ) {
            } else return false;
            if(profileModel.getUsername() != null && ! profileModel.getUsername().isBlank()) {
            } else return false;
            if(profileModel.getPassword() != null && ! profileModel.getPassword().isBlank()) {
            } else return false;
            if(profileModel.getPhone() != null && ! profileModel.getPhone().isBlank()) {
            } else return false;
        } else return false;
        return true;
    }

    public static boolean isUpdatablePost(PostsModel postsModel) {
        if(postsModel.getDeleted() == false) {
            if(postsModel.getTitle() != null && ! postsModel.getTitle().isBlank()) {
            } else return false;
            if(postsModel.getDescription() != null && ! postsModel.getDescription().isBlank()) {
            } else return false;
            if(postsModel.getPhotoLink() != null && ! postsModel.getPhotoLink().isBlank()) {
            } else return false;
            if(postsModel.getVideoLink() != null && ! postsModel.getVideoLink().isBlank()) {
            } else return false;
         } else return false;
        return true;
    }
}
