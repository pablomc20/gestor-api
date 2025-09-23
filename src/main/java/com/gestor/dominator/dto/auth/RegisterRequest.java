package com.gestor.dominator.dto.auth;

import lombok.Builder;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.util.List;

@Builder
public record RegisterRequest(
    @NotEmpty(message = "El nombre de usuario es requerido")
    @Size(min = 2, max = 100, message = "El nombre de usuario debe tener entre 2 y 100 caracteres")
    String username,

    @NotEmpty(message = "La contraseña es requerida")
    String password,
    
    List<String> roles
) {
}