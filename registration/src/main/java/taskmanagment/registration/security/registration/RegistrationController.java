package taskmanagment.registration.security.registration;


import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import taskmanagment.registration.data.UserRepository;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    private UserRepository userRepo;
    private PasswordEncoder passwordEncoder;

    public RegistrationController(
            UserRepository userRepo, PasswordEncoder passwordEncoder){
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String registrationForm(Model model){
        model.addAttribute("registerForm", new RegistrationForm());
        return "registration";
    }

    @PostMapping
    public String processRegistration(@ModelAttribute("registerForm") @Valid RegistrationForm form,
                                      Errors errors, Model model){
        if(errors.hasErrors()) return "registration";

        if (userRepo.findByUsername(form.getUsername()).isPresent()) {
            model.addAttribute("error", "Username already in use");
            return "registration";
        }

        try {
            userRepo.save(form.toUser(passwordEncoder));
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("error", "Username already in use");
            return "registration";
        }

        return "redirect:/login";
    }

}
