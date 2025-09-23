package com.gestor.dominator.dto.product;

import lombok.Builder;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

@Builder
public record ProductRequest(
    @NotEmpty(message = "El título del producto es requerido")
    @Size(min = 2, max = 100, message = "El título debe tener entre 2 y 100 caracteres")
    String title,

    @NotEmpty(message = "La categoría es requerida")
    String category,

    @Size(max = 500, message = "La descripción no puede exceder los 500 caracteres")
    String description,

    List<String> images,
    List<String> tags,

    String dimensions,
    String color,

    @NotNull(message = "El precio es requerido")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor a 0")
    Double price
) {

}