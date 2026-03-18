package taskmanagment.registration.web.login;

import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Controller
public class AuthController {

    private final UserService userService;

    AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public Mono<String> loginPage() {
        return Mono.just("login");
    }

    @PostMapping("/login")
    public Mono<String> login(ServerWebExchange exchange,
                              Model model) {
        return exchange.getFormData()
                .flatMap(formData -> {
                    String username = formData.getFirst("username");
                    String password = formData.getFirst("password");

                    return userService.authenticate(username, password)
                            .flatMap(user -> {
                                String token = JwtService.generateToken(user.getUsername());
                                exchange.getResponse().getHeaders().add("Authorization", "Bearer " + token);
                                return Mono.just("login");
                            })
                            .onErrorResume(BadCredentialsException.class, ex -> {
                                model.addAttribute("error", "Неверный логин или пароль");
                                return Mono.just("login");
                            });
                });
    }
}
