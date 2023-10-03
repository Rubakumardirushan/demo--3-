package com.example.demo.webs;




import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .csrf().disable() // Disable CSRF protection
            .authorizeHttpRequests(auth -> {
                auth.requestMatchers("/user").authenticated();
                auth.requestMatchers("/home").authenticated();
                auth.requestMatchers("/").authenticated();
                auth.anyRequest().authenticated();
            })
            .oauth2Login(withDefaults())
            .formLogin(withDefaults())
            .formLogin(withDefaults())
            .build();
    }
}
