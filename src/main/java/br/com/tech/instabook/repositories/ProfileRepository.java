package br.com.tech.instabook.repositories;

import br.com.tech.instabook.models.ProfileModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<ProfileModel, Long> {

    List<ProfileModel> findAll();
    UserDetails findByUsername(String username);
    boolean existsByEmail(String email);
    Optional<ProfileModel> findProfileModelById(Long id);

    Optional<ProfileModel> deleteProfileModelById(Long id);

    List<ProfileModel> findProfileModelByDeletedIsTrue();
    List<ProfileModel> findProfileModelByDeletedIsFalse();

    boolean existsByPhone(String phone);

    boolean existsByUsername(String username);

    Optional<ProfileModel> findByUsernameContainingIgnoreCase(String username);
}
