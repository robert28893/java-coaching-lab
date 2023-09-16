package vn.unigap.java.authentication;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.converter.RsaKeyConverters;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@Log4j2
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues()))
//                .cors(cfg -> cfg.disable())
                .csrf(cfg -> cfg.disable())
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/auth/login").permitAll()
                                .anyRequest().authenticated()
                )
                .oauth2ResourceServer(configurer -> {
                    configurer.jwt(jwtConfigurer -> {
                        try {
                            jwtConfigurer.decoder(NimbusJwtDecoder.withPublicKey(readPublicKey(
                                    new ClassPathResource("public.pem"))).build());
                        } catch (Exception e) {
                            log.error("Error: ", e);
                            throw new RuntimeException(e);
                        }
                    });
                })
//                .formLogin((form) -> form
//                        .loginPage("/login")
//                        .permitAll()
//                )
//                .logout((logout) -> logout.permitAll())
        ;

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user =
                User.withDefaultPasswordEncoder()
                        .username("user")
                        .password("password")
                        .roles("USER")
                        .build();

        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public JwtEncoder jwtEncoder() {
        try {
            return new NimbusJwtEncoder(new ImmutableJWKSet<>(
                    new JWKSet(
                            new RSAKey.Builder(readPublicKey(new ClassPathResource("public.pem")))
                                    .privateKey(readPrivateKey(new ClassPathResource("private.pem")))
                                    .build()
                    )
            ));
        } catch (Exception e) {
            log.error("Error: ", e);
            throw new RuntimeException(e);
        }
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    private static RSAPublicKey readPublicKey(Resource resource) throws Exception {
        return RsaKeyConverters.x509().convert(resource.getInputStream());
//        try (FileReader keyReader = new FileReader(resource.getFile())) {
//            PEMParser pemParser = new PEMParser(keyReader);
//            JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
//            SubjectPublicKeyInfo publicKeyInfo = SubjectPublicKeyInfo.getInstance(pemParser.readObject());
//            return (RSAPublicKey) converter.getPublicKey(publicKeyInfo);
//        }
    }

    private static RSAPrivateKey readPrivateKey(Resource resource) throws Exception {
        return RsaKeyConverters.pkcs8().convert(resource.getInputStream());
//        try (FileReader keyReader = new FileReader(resource.getFile())) {
//            PEMParser pemParser = new PEMParser(keyReader);
//            JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
//            SubjectPublicKeyInfo publicKeyInfo = SubjectPublicKeyInfo.getInstance(pemParser.readObject());
//            return (RSAPublicKey) converter.getPublicKey(publicKeyInfo);
//        }
    }
}
