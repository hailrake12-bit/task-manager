package taskmanagment.registration;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Controller
public class HomeController {

    @GetMapping("/")
    public Mono<Void> redirectToNewPath(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.FOUND);
        exchange.getResponse().getHeaders().setLocation(java.net.URI.create("/login"));
        return exchange.getResponse().setComplete();
    }

}
