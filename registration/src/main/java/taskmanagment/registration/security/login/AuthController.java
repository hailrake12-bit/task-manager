package taskmanagment.registration.security.login;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Mono;

@Controller
public class AuthController {

    @GetMapping("/login")
    public Mono<String> loginPage() {
        return Mono.just("login");
    }

}
