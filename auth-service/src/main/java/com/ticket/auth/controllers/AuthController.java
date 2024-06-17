package com.ticket.auth.controllers;

import com.ticket.auth.entities.AuthRequest;
import com.ticket.auth.entities.AuthResponse;
import com.ticket.auth.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    private Environment env;

    @Autowired
    public AuthController(final AuthService authService) {
        this.authService = authService;
    }

    @RequestMapping("")
    public String home() {
        return "Auth Service running at port: " + env.getProperty("local.server.port");
    }

    @PostMapping(value = "/register")
    public ResponseEntity<AuthResponse> register(@RequestBody AuthRequest authRequest) {
        return ResponseEntity.ok(authService.register(authRequest));
    }

}
