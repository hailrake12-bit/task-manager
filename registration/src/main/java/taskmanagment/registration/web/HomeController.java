package taskmanagment.registration.web;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.server.ServerWebExchange;

@Controller
public class HomeController {

    @GetMapping("/")
    public String redirect() {
        return "redirect:/login";
    }
}
