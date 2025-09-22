package com.gestor.dominator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO para respuestas de error estandarizadas.
 * Todas las excepciones personalizadas devolverán este formato.
 */
@Data
@NoArgsConstructor
public class ErrorResponse {

    private String error;
    private String description;
    private int status;

    public ErrorResponse(String error, String description, int status) {
        this.error = error;
        this.description = description;
        this.status = status;
    }
}