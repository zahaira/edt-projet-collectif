package com.example.edt_k.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().authenticated() // Secure all requests
                )
                .formLogin(form -> form
                        .loginPage("/") // Custom login page
                        .defaultSuccessUrl("/edt/home", true) // Redirect to /edt/home after successful login
                        .permitAll()
                )
                .csrf(csrf -> csrf.disable()); // Disable CSRF for testing

        return http.build();
    }
}
