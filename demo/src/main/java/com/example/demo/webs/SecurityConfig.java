package com.example.demo.webs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity https) throws Exception {
        https
            .csrf().disable() // Disable CSRF protection
            .authorizeRequests(authorize -> {
                authorize
                .requestMatchers("/error").permitAll() 
                    .requestMatchers("/").permitAll() // Allow access to /login for all
                    .anyRequest().authenticated(); // Require authentication for other URLs
            })
            .oauth2Login(withDefaults()); // Configure OAuth2 login

        // Handle access denied by redirecting to the home page
        https
            .exceptionHandling()
            .accessDeniedHandler((request, response, accessDeniedException) -> response.sendRedirect("/"));

        return https.build();
    }



    
}
