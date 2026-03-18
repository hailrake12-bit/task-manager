package taskmanagment.registration.web.registration;

import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;
import taskmanagment.registration.data.UserRepository;
import org.springframework.ui.Model;
import taskmanagment.registration.web.login.UserService;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    public RegistrationController(PasswordEncoder passwordEncoder, UserService userService){
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @GetMapping
    public String registrationForm(Model model){
        model.addAttribute("registerForm", new RegistrationForm());
        return "registration";
    }

//    @PostMapping
//    public Mono<String> processRegistration(@ModelAttribute("registerForm") @Valid RegistrationForm form,
//                                      Errors errors, Model model){
//        if(errors.hasErrors()) return Mono.just("registration");
//
//        if (userService.isUserExist(form.getUsername())) {
//            model.addAttribute("error", "Username already in use");
//            return  Mono.just("registration");
//        }
//
//        try {
//            userService.createUser(form.toUser(passwordEncoder));
//        } catch (DataIntegrityViolationException e) {
//            model.addAttribute("error", "Username already in use");
//            return  Mono.just("registration");
//        }
//
//        return  Mono.just("redirect:/login");
//    }

    @PostMapping
    public Mono<String> processRegistration(@ModelAttribute("registerForm") @Valid RegistrationForm form,
                                            Errors errors, Model model) {
        if (errors.hasErrors()) return Mono.just("registration");

        return userService.createUser(form.toUser(passwordEncoder))
                .map(user -> "redirect:/login")
                .onErrorResume(DataIntegrityViolationException.class, e -> {
                    model.addAttribute("error", "Username already in use");
                    return Mono.just("registration");
                });
    }

}
