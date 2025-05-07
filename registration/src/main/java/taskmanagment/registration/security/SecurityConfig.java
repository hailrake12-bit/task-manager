package taskmanagment.registration.security;

import jakarta.servlet.http.HttpServletRequestWrapper;
import taskmanagment.registration.security.login.User;
import taskmanagment.registration.data.UserRepository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import taskmanagment.registration.security.login.LoginValidationFilter;


@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/register", "/styles.css").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .failureHandler(authenticationFailureHandler())
                        .permitAll()
                )
                .addFilterBefore(loginValidationFilter(), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public LoginValidationFilter loginValidationFilter() {
        return new LoginValidationFilter();
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return (request, response, exception) -> {
            request.getSession().setAttribute("LOGIN_ERROR", "Invalid username or password");
            //  response.sendRedirect("/login"); SIMPLE SOLUTION WITH REDIRECT

            // SOLUTION WITH CHANGING HTTP-VERB AND TRANSFERRING CONTROL TO OTHER SERVLET ON SERVER-SIDE
            HttpServletRequestWrapper wrappedRequest = new HttpServletRequestWrapper(request) {
                @Override
                public String getMethod() {
                    return "GET";
                }
            };
            request.getRequestDispatcher("/login").forward(wrappedRequest, response);
        };
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepo){
        return username -> {
            User user = userRepo.findByUsername(username);
            if(user != null) return user;

            throw new UsernameNotFoundException("User '" + username + "' not found");
        };
    }
}
