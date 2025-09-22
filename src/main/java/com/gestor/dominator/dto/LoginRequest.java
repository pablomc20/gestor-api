package com.gestor.dominator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    @NotEmpty(message = "El nombre de usuario es requerido")
    private String username;

    @NotEmpty(message = "La contraseña es requerida")
    private String password;
}