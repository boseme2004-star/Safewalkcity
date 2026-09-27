package com.safewalk.demo.controller;

import com.safewalk.demo.dto.LoginRequest;
import com.safewalk.demo.dto.RegisterRequest;
import com.safewalk.demo.model.User;
import com.safewalk.demo.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @Valid @RequestBody RegisterRequest request) {

        try {
            User user = authService.register(request);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body("Registration successful");

        } catch (RuntimeException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    @PostMapping("/login")
public ResponseEntity<?> login(
        @Valid @RequestBody LoginRequest request) {

    try {

        User user = authService.login(
                request.getEmail(),
                request.getPassword()
        );

        return ResponseEntity.ok("Login successful");

    } catch (RuntimeException e) {

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(e.getMessage());
    }
}
}