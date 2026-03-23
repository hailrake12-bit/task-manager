package taskmanagment.registration.web.login;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

//    private final UserService userService;
//
//    AuthController(UserService userService) {
//        this.userService = userService;
//    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

}
