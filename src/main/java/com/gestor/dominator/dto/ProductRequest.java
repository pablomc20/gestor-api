package com.gestor.dominator.dto;

import lombok.Data;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

@Data
public class ProductRequest {

    @NotEmpty(message = "El título del producto es requerido")
    @Size(min = 2, max = 100, message = "El título debe tener entre 2 y 100 caracteres")
    private String title;

    @NotEmpty(message = "La categoría es requerida")
    private String category;

    @Size(max = 500, message = "La descripción no puede exceder los 500 caracteres")
    private String description;

    private List<String> images;
    private List<String> tags;

    private String dimensions;
    private String color;

    @NotNull(message = "El precio es requerido")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor a 0")
    private Double price;
}