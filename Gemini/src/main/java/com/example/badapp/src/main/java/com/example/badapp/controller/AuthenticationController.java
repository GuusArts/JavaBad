package com.example.badapp.controller;

import com.example.badapp.model.User;
import com.example.badapp.service.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private SessionManager sessionManager;

    @PostMapping("/login")
    public String login(@RequestBody User user) {
        if (user.getName().equals("admin") && user.getPassword().equals("password")) {
            return sessionManager.createSession(user.getName()); // Onveilige sessiecreatie
        }
        return "Login mislukt";
    }

    @GetMapping("/checkSession/{sessionId}")
    public String checkSession(@PathVariable String sessionId) {
        return sessionManager.getSessionUser(sessionId);
    }
}