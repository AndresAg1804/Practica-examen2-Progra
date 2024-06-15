package com.example.practica_examen2.presentation;

import com.example.practica_examen2.security.UserDetailsImp;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import com.example.practica_examen2.logic.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/login")
public class Login {

    @PostMapping("/login")
    public User login(@RequestBody User form,  HttpServletRequest request) {
        try {
            request.login(form.getEmail(), form.getPassword());
        } catch (ServletException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        Authentication auth = (Authentication) request.getUserPrincipal();
        User user = ((UserDetailsImp) auth.getPrincipal()).getUser();
        return new User(user.getEmail(), user.getName() ,null, user.getRole());
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request) {
        try {
            request.logout();
        } catch (ServletException e) {
        }
    }

    @GetMapping("/current-user")
    public User getCurrentUser(@AuthenticationPrincipal UserDetailsImp user) {
        return new User(user.getUser().getEmail(), user.getUser().getName(),null, user.getUser().getRole());
    }
}

