package com.gestor.dominator.controller;

import com.gestor.dominator.dto.AuthResponse;
import com.gestor.dominator.dto.LoginRequest;
import com.gestor.dominator.dto.RegisterRequest;
import com.gestor.dominator.exceptions.custom.DataValidationException;
import com.gestor.dominator.model.User;
import com.gestor.dominator.repository.UserRepository;
import com.gestor.dominator.service.config.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword()
            )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = (User) authentication.getPrincipal();

        String token = jwtUtil.generateToken(user);

        AuthResponse response = new AuthResponse(token);

        return ResponseEntity.ok(response);
    }

    // @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest registerRequest) {
        // Verificar si el usuario ya existe
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new DataValidationException("Ya existe un usuario con el nombre de usuario: " + registerRequest.getUsername());
        }

        // Crear nuevo usuario
        List<String> roles = registerRequest.getRoles() != null && !registerRequest.getRoles().isEmpty()
            ? registerRequest.getRoles()
            : Arrays.asList("USER");

        User user = new User(
            registerRequest.getUsername(),
            passwordEncoder.encode(registerRequest.getPassword()),
            roles
        );

        userRepository.save(user);

        return ResponseEntity.ok("Usuario registrado exitosamente");
    }
}