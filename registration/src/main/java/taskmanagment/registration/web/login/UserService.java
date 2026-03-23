package taskmanagment.registration.web.login;

import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import taskmanagment.registration.data.UserRepository;
import taskmanagment.registration.web.entity.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.security.Key;
import java.util.Date;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       UserDetailsService userDetailsService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

//    public boolean isUserExist(String username) {
//        return userRepository.findByUsername(username).isPresent();
//    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

//    public User authenticate(String username, String password) {
//        return userRepository.findByUsername(username)
//                .flatMap(user -> {
//                    if (passwordEncoder.matches(password, user.getPassword())) {
//                        return Mono.just(user);
//                    } else {
//                        return Mono.error(new BadCredentialsException("Bad credentials"));
//                    }
//                });
//    }


}
