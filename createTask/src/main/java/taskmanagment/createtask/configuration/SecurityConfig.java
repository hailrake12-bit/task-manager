package taskmanagment.createtask.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityFilterChain() {
        ServerHttpSecurity http = ServerHttpSecurity.http();
        return http
                .authorizeExchange(auth -> auth
                        .anyExchange().permitAll()
                )
                .build();
    }
}

