package com.gestor.dominator.controller;

import com.gestor.dominator.dto.auth.AuthResponse;
import com.gestor.dominator.dto.auth.LoginRequest;
import com.gestor.dominator.dto.auth.RegisterRequest;
import com.gestor.dominator.service.product.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        AuthResponse response = userService.authLogin(loginRequest);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        userService.registerNewUser(request);
        return ResponseEntity.ok("Usuario registrado exitosamente");
    }

}