package taskmanagment.registration.security.login;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {

    @GetMapping
    public String showLoginForm(Model model, HttpServletRequest request){
        String loginErrorMessage = (String) request.getSession().getAttribute("LOGIN_ERROR");
        String fieldsErrorMessage = (String) request.getSession().getAttribute("FIELDS_ERROR");

        if (loginErrorMessage != null) {
            model.addAttribute("error", loginErrorMessage);
            request.getSession().removeAttribute("LOGIN_ERROR");
        }

        if (fieldsErrorMessage != null) {
            model.addAttribute("error", fieldsErrorMessage);
            request.getSession().removeAttribute("FIELDS_ERROR");
        }

        return "login";
    }

}
