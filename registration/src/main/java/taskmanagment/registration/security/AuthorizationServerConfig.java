    package taskmanagment.registration.security;

    import com.nimbusds.jose.jwk.JWKSet;
    import com.nimbusds.jose.jwk.RSAKey;
    import com.nimbusds.jose.jwk.source.JWKSource;
    import com.nimbusds.jose.proc.SecurityContext;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.core.annotation.Order;
    import org.springframework.security.config.annotation.web.builders.HttpSecurity;
    import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
    import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
    import org.springframework.security.core.userdetails.User;
    import org.springframework.security.core.userdetails.UserDetails;
    import org.springframework.security.core.userdetails.UserDetailsService;
    import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
    import org.springframework.security.crypto.factory.PasswordEncoderFactories;
    import org.springframework.security.crypto.password.NoOpPasswordEncoder;
    import org.springframework.security.crypto.password.PasswordEncoder;
    import org.springframework.security.oauth2.core.AuthorizationGrantType;
    import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
    import org.springframework.security.oauth2.jwt.JwtDecoder;
    import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
    import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
    import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
    import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
    import org.springframework.security.provisioning.InMemoryUserDetailsManager;
    import org.springframework.security.web.SecurityFilterChain;

    import java.security.KeyPair;
    import java.security.KeyPairGenerator;
    import java.security.interfaces.RSAPrivateKey;
    import java.security.interfaces.RSAPublicKey;
    import java.util.UUID;

    @Configuration
    @EnableWebSecurity
    public class AuthorizationServerConfig {

        @Bean
        public PasswordEncoder passwordEncoder() {
            return PasswordEncoderFactories.createDelegatingPasswordEncoder();
        }

        @Bean
        @Order(1)
        public SecurityFilterChain authServerSecurityFilterChain(HttpSecurity http) throws Exception {
            // Настройка Authorization Server
            OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
            return http.build();
        }

        @Bean
        @Order(2)
        public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
            http
                    .authorizeHttpRequests(auth -> auth
                            .anyRequest().permitAll()
                    )
                    .csrf(AbstractHttpConfigurer::disable)
                    .formLogin(form -> form
                            .loginPage("/login")  // GET /login возвращает HTML форму
                            .permitAll()
                    );

            return http.build();
        }

        @Bean
        public RegisteredClientRepository registeredClientRepository() {
            RegisteredClient client = RegisteredClient.withId(UUID.randomUUID().toString())
                    .clientId("gateway")
                    .clientSecret("{noop}gateway-secret") // для теста можно оставить noop
                    .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                    .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                    .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                    .redirectUri("http://localhost:8082/login/oauth/authorize")
                    .scope("profile")
                    .scope("email")
                    .build();

            return new InMemoryRegisteredClientRepository(client);
        }

        @Bean
        public UserDetailsService users(PasswordEncoder encoder) {
            UserDetails user = User.builder()
                    .username("user")
                    .password(encoder.encode("password"))
                    .roles("USER")
                    .build();

            System.out.println("Stored password: " + user.getPassword());

            return new InMemoryUserDetailsManager(user);
        }

        @Bean
        public JWKSource<SecurityContext> jwkSource() {
            RSAKey rsaKey = generateRsa();
            JWKSet jwkSet = new JWKSet(rsaKey);
            return (selector, context) -> selector.select(jwkSet);
        }

        private static RSAKey generateRsa() {
            KeyPair keyPair = generateRsaKey();
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

            return new RSAKey.Builder(publicKey)
                    .privateKey(privateKey)
                    .keyID(UUID.randomUUID().toString())
                    .build();
        }

        private static KeyPair generateRsaKey() {
            KeyPair keyPair;
            try {
                KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
                keyPairGenerator.initialize(2048);
                keyPair = keyPairGenerator.generateKeyPair();
            } catch (Exception ex) {
                throw new IllegalStateException(ex);
            }
            return keyPair;
        }

        @Bean
        public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
            return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
        }
    }
