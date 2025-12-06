package com.gestor.dominator.dto.projects;

import java.util.UUID;

import jakarta.validation.Valid;
import lombok.Builder;

@Builder
public record DetailsForClientRq(
        // Validar que sea un UUID válido
        @jakarta.validation.constraints.NotNull @Valid UUID projectId) {

}
