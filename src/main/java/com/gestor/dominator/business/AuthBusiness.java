package com.gestor.dominator.business;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gestor.dominator.dto.auth.AuthResponse;
import com.gestor.dominator.dto.auth.LoginRequest;
import com.gestor.dominator.dto.auth.RegisterRequest;
import com.gestor.dominator.exceptions.custom.AuthenticationException;
import com.gestor.dominator.model.postgre.auth.User;
import com.gestor.dominator.model.postgre.auth.UserDetail;
import com.gestor.dominator.repository.UserRepository;
import com.gestor.dominator.service.config.JwtUtil;
import com.gestor.dominator.service.product.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthBusiness implements UserService {

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;

    @Override
    public AuthResponse authLogin(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.email(),
                        loginRequest.password()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = (User) authentication.getPrincipal();

        String token = jwtUtil.generateToken(user);

        return AuthResponse.builder()
                .email(user.getEmail())
                .name(user.getUserDetail().getName())
                .uriImage(user.getUserDetail().getUrl_image())
                .id(user.getUserId().toString())
                .token(token).build();
    }

    @Override
    public void registerNewUser(RegisterRequest registerRequest) {
        // Verificar si el usuario ya existe
        if (userRepository.existsByEmail(registerRequest.email())) {
            throw new AuthenticationException("El email ya está en uso.");
        }

        User user = User.builder()
                .email(registerRequest.email())
                .password(passwordEncoder.encode(registerRequest.password()))
                .role("CLIENT") // 'CLIENT' por defecto
                .enabled(true) // Habilitado por defecto
                .build();

        UserDetail detail = UserDetail.builder()
                .name(registerRequest.name())
                .phone(registerRequest.phone())
                .build();

        detail.setUser(user);
        user.setUserDetail(detail);

        userRepository.save(user);
    }

}
