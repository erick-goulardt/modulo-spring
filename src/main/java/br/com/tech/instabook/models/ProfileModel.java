package br.com.tech.instabook.models;

import br.com.tech.instabook.util.ProfileRole;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Entity @Table(name = "profile") @AllArgsConstructor @NoArgsConstructor @Getter @Setter
public class ProfileModel implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_id")
    private Long id;
    @NotNull
    private Boolean deleted = false;
    @JsonFormat(pattern = "yyyy/MM/dd") @NotNull
    private LocalDate createdAt = LocalDate.now();
    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDate updatedAt;
    @Column(nullable = false) @NotEmpty
    private String name;
    @Column(nullable = false) @NotEmpty
    private String username;
    @Column(nullable = false) @Pattern(regexp = "^\\d{11}$") @NotEmpty
    private String phone;
    @Column(nullable = false, unique = true) @Email(message = "Email is not valid", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    @NotEmpty(message = "Email cannot be empty")
    private String email;
    @Column(nullable = false) @NotEmpty
    private String password;
    @NotEmpty @Column(nullable = false)
    private String profileLink = "www.instabook.com/";

    private ProfileRole role;
    public boolean changeActivity() {
        this.setDeleted(!this.getDeleted());
        return this.getDeleted();
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.role == ProfileRole.ADMIN) return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        else return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return deleted;
    }
}
