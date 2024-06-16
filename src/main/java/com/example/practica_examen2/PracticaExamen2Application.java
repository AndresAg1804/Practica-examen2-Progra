package com.example.practica_examen2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

@SpringBootApplication
public class PracticaExamen2Application {

    public static void main(String[] args) {
        SpringApplication.run(PracticaExamen2Application.class, args);
    }

    @Bean("securityFilterChain")
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        var chain = http
                .authorizeHttpRequests(customizer -> customizer
                        .requestMatchers("/api/login/login").permitAll()
                        .requestMatchers("/api/login/logout").authenticated()
                        .requestMatchers("/**").permitAll()
                )
                .exceptionHandling(customizer -> customizer
                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
                .csrf().disable()
                .build();
        return chain;
    }
}
