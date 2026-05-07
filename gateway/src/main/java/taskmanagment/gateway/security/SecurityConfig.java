package taskmanagment.gateway.security;

import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultReactiveOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.server.ServerOAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import taskmanagment.gateway.InMemoryOAuth2AuthorizationRequestRepository;

import java.net.URI;


@Configuration
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http,
                                                         InMemoryOAuth2AuthorizationRequestRepository authRequestRepository) {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeExchange(exchange -> exchange
                        .pathMatchers("/styles.css").permitAll()
                        .anyExchange().authenticated()
                )
                .oauth2Login(oauth2 -> oauth2
                        .authorizationRequestRepository(authRequestRepository)
                        .authenticationSuccessHandler(
                                new RedirectServerAuthenticationSuccessHandler("/")
                        )
                        .authenticationFailureHandler((webFilterExchange, exception) -> {
                            System.err.println("=== AUTH FAILED: " + exception.getClass().getName());
                            System.err.println("=== MESSAGE: " + exception.getMessage());
                            exception.printStackTrace();
                            var exchange = webFilterExchange.getExchange();
                            exchange.getResponse().setStatusCode(HttpStatus.FOUND);
                            exchange.getResponse().getHeaders().setLocation(URI.create("/login?error"));
                            return exchange.getResponse().setComplete();
                        })
                )
                .build();
    }

    @Component
    @Order(-2)
    public class GlobalErrorHandler implements ErrorWebExceptionHandler {
        @Override
        public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
            System.err.println("=== GLOBAL ERROR: " + ex.getClass().getName());
            System.err.println("=== MESSAGE: " + ex.getMessage());
            ex.printStackTrace();
            return Mono.error(ex);
        }
    }

    @Bean
    public ReactiveOAuth2AuthorizedClientManager authorizedClientManager(
            ReactiveClientRegistrationRepository clientRegistrationRepository,
            ServerOAuth2AuthorizedClientRepository authorizedClientRepository) {
        return new DefaultReactiveOAuth2AuthorizedClientManager(
                clientRegistrationRepository, authorizedClientRepository);
    }

}

