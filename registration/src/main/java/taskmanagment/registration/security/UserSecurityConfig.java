//package taskmanagment.registration.security;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//
//@Configuration
//public class UserSecurityConfig {
//    @Bean
//    public UserDetailsService users(PasswordEncoder encoder) {
//        UserDetails user = User.builder()
//                .username("user")
//                .password(encoder.encode("password"))
//                .roles("USER")
//                .build();
//
//        return new InMemoryUserDetailsManager(user);
//    }
//}
