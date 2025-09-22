package com.gestor.dominator.dto;

import lombok.Data;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Data
public class RoomRequest {

    @NotEmpty(message = "El nombre de la sala es requerido")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String name;

    @NotEmpty(message = "El slug de la sala es requerido")
    @Pattern(regexp = "^[a-z0-9-]+$", message = "El slug solo puede contener letras minúsculas, números y guiones")
    @Size(min = 2, max = 50, message = "El slug debe tener entre 2 y 50 caracteres")
    private String slug;
}