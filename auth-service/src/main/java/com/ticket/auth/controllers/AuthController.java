package com.ticket.auth.controllers;

import com.ticket.auth.entities.RegisterRequest;
import com.ticket.auth.entities.RegisterResponse;
import com.ticket.auth.entities.LoginRequest;
import com.ticket.auth.entities.LoginResponse;
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
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok(authService.register(registerRequest));
    }

    @PostMapping(value = "/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

}
