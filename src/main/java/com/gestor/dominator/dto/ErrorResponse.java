package com.gestor.dominator.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para respuestas de error estandarizadas.
 * Todas las excepciones personalizadas devolverán este formato.
 */
@Builder
public record ErrorResponse(
    String error,
    String description,
    int status
) {

}