package com.gestor.dominator.dto.auth;

import lombok.Builder;
import jakarta.validation.constraints.NotEmpty;

@Builder
public record LoginRequest(
    @NotEmpty(message = "El nombre de usuario es requerido")
    String username,
    @NotEmpty(message = "La contraseña es requerida")
    String password
) {
}
