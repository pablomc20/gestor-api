package com.gestor.dominator.controller;

import com.gestor.dominator.dto.auth.AuthResponse;
import com.gestor.dominator.dto.auth.LoginRequest;
import com.gestor.dominator.dto.auth.RegisterRequest;
import com.gestor.dominator.exceptions.custom.DataValidationException;
import com.gestor.dominator.model.User;
import com.gestor.dominator.repository.UserRepository;
import com.gestor.dominator.service.config.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "API for user authentication and registration")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    @Operation(summary = "User login", description = "Authenticate user and return JWT token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.username(),
                        loginRequest.password()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = (User) authentication.getPrincipal();

        String token = jwtUtil.generateToken(user);

        return ResponseEntity.ok(
                AuthResponse.builder()
                        .id(user.getId())
                        .name(user.getUsername())
                        .email(user.getEmail())
                        .token(token)
                        .build());
    }

    @PostMapping("/register")
    @Operation(summary = "User registration", description = "Register a new user account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest registerRequest) {
        // Verificar si el usuario ya existe
        if (userRepository.existsByUsername(registerRequest.username())) {
            throw new DataValidationException(
                    "Ya existe un usuario con el nombre de usuario: " + registerRequest.username());
        }

        // Crear nuevo usuario
        List<String> roles = registerRequest.roles() != null && !registerRequest.roles().isEmpty()
                ? registerRequest.roles()
                : Arrays.asList("USER");

        User user = User.builder()
                .username(registerRequest.username())
                .password(passwordEncoder.encode(registerRequest.password()))
                .email(registerRequest.email())
                .roles(roles)
                .build();

        userRepository.save(user);

        return ResponseEntity.ok("Usuario registrado exitosamente");
    }

}