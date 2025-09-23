package com.gestor.dominator.controller;

import com.gestor.dominator.dto.auth.AuthResponse;
import com.gestor.dominator.dto.auth.LoginRequest;
import com.gestor.dominator.dto.auth.RegisterRequest;
import com.gestor.dominator.exceptions.custom.DataValidationException;
import com.gestor.dominator.model.User;
import com.gestor.dominator.repository.UserRepository;
import com.gestor.dominator.service.config.JwtUtil;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.username(),
                loginRequest.password()
            )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = (User) authentication.getPrincipal();

        String token = jwtUtil.generateToken(user);

        return ResponseEntity.ok(
            AuthResponse.builder().token(token).build());
    }

    // @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest registerRequest) {
        // Verificar si el usuario ya existe
        if (userRepository.existsByUsername(registerRequest.username())) {
            throw new DataValidationException("Ya existe un usuario con el nombre de usuario: " + registerRequest.username());
        }

        // Crear nuevo usuario
        List<String> roles = registerRequest.roles() != null && !registerRequest.roles().isEmpty()
            ? registerRequest.roles()
            : Arrays.asList("USER");

        User user = new User(
            registerRequest.username(),
            passwordEncoder.encode(registerRequest.password()),
            roles
        );

        userRepository.save(user);

        return ResponseEntity.ok("Usuario registrado exitosamente");
    }

}