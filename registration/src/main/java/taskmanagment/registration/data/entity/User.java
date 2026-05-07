package taskmanagment.registration.data.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Data
@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class User implements UserDetails {


    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;

    @NotNull
    @Column
    private String username;

    @NotNull
    private String password;

    public User(String username, String password){
        this.username = username;
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }
}
