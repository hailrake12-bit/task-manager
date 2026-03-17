package taskmanagment.registration.security;

import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import taskmanagment.registration.security.login.User;
import taskmanagment.registration.data.UserRepository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;


@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) throws Exception {
        return http
                .authorizeExchange(ex -> ex.anyExchange().permitAll())
                .build();
    }
    

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepo){
        return username -> {
            Optional<User> user = userRepo.findByUsername(username);
            if(user.isPresent()) return user.get();

            throw new UsernameNotFoundException("User '" + username + "' not found");
        };
    }
}
