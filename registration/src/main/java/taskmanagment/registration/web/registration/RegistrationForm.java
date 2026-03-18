package taskmanagment.registration.web.registration;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;
import taskmanagment.registration.web.entity.User;

@Data
public class RegistrationForm {

    @NotNull
    @Size(min =3, max = 20, message = "Username must be between 3 and 20 characters long")
    private String username;

    @NotNull
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    @NotNull
    private String confirmation;

    @AssertTrue(message = "Passwords must match")
    public boolean isPasswordsMatching(){
        return (password != null && password.equals(confirmation));
    }

    public User toUser(PasswordEncoder passwordEncoder){
        return new User(username, passwordEncoder.encode(password));
    }
}
