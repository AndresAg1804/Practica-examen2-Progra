package com.example.practica_examen2.presentation;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import com.example.practica_examen2.logic.User;

@RestController
@RequestMapping("/login")
public class Login {

    @PostMapping("/login")
    public User login(@RequestBody User form, HttpServletRequest request) {
        try {
            request.login(form.getId(), form.getPassword());
        } catch (ServletException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        Authentication auth = (Authentication) request.getUserPrincipal();
        User user = ((UserDetailsImp) auth.getPrincipal()).getUser();
        return new User(user.getId(), null, user.getRol());
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
        return new User(user.getUser().getId(), null, user.getUser().getRol()); //hay que ver como sacar esto de sessionStorage
    }
}
