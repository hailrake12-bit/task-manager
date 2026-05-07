package taskmanagment.registration.web.registration;

import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import taskmanagment.registration.web.UserService;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String registrationForm(Model model) {
        model.addAttribute("registerForm", new RegistrationForm());
        return "registration";
    }

    @PostMapping
    public String processRegistration(@ModelAttribute("registerForm") @Valid RegistrationForm form,
                                      Errors errors, Model model) {
        if (errors.hasErrors()) return "registration";

        if (userService.isUserExist(form)) {
            model.addAttribute("error", "Username already in use");
            return "registration";
        }

        try {
            userService.createUser(form);
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("error", "Username already in use");
            return "registration";
        }

        return "redirect:/login";
    }
}