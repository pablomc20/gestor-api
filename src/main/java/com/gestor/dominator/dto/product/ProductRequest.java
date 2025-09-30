package com.gestor.dominator.dto.product;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

@Builder
@Schema(description = "Product request DTO", example = """
{
  "title": "Modern Chair",
  "category": "507f1f77bcf86cd799439011",
  "description": "A comfortable modern chair",
  "images": ["507f1f77bcf86cd799439012"],
  "tags": ["507f1f77bcf86cd799439013"],
  "dimensions": "30x30x40 cm",
  "color": "Black",
  "price": 299.99
}
""")
public record ProductRequest(
    @Schema(description = "Product title", example = "Modern Chair", required = true)
    @NotEmpty(message = "El título del producto es requerido")
    @Size(min = 2, max = 100, message = "El título debe tener entre 2 y 100 caracteres")
    String title,

    @Schema(description = "Category ID", example = "507f1f77bcf86cd799439011", required = true)
    @NotEmpty(message = "La categoría es requerida")
    String category,

    @Schema(description = "Product description", example = "A comfortable modern chair")
    @Size(max = 500, message = "La descripción no puede exceder los 500 caracteres")
    String description,

    @Schema(description = "List of image IDs", example = "[\"507f1f77bcf86cd799439012\"]")
    List<String> images,
    @Schema(description = "List of tag IDs", example = "[\"507f1f77bcf86cd799439013\"]")
    List<String> tags,

    @Schema(description = "Product dimensions", example = "30x30x40 cm")
    String dimensions,
    @Schema(description = "Product color", example = "Black")
    String color,

    @Schema(description = "Product price", example = "299.99", required = true)
    @NotNull(message = "El precio es requerido")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor a 0")
    Double price
) {

}