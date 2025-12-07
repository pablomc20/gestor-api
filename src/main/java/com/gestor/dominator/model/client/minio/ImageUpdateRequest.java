package com.gestor.dominator.model.client.minio;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

@Builder
public record ImageUpdateRequest(
        @NotEmpty(message = "El nombre original del archivo es requerido") String originalName) {
    // Otros campos que se puedan actualizar
}