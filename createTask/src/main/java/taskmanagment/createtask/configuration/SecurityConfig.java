package taskmanagment.createtask.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable) // 🔥 обязательно
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable) // 🔥 желательно
                .authorizeExchange(auth -> auth.anyExchange().permitAll())
                .build();
    }
}

