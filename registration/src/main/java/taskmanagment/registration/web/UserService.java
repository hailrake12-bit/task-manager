package taskmanagment.registration.web;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import taskmanagment.registration.data.UserRepository;
import taskmanagment.registration.data.entity.User;
import taskmanagment.registration.web.registration.RegistrationForm;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean isUserExist(RegistrationForm registrationForm) {
        return userRepository.findByUsername(registrationForm.getUsername()).isPresent();
    }

    public void createUser(RegistrationForm form) {
        User user = form.toUser(passwordEncoder);
        userRepository.save(user);
    }

}
