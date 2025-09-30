package com.gestor.dominator.dto.product;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import java.time.LocalDateTime;
import java.util.List;

import com.gestor.dominator.dto.image.ImageResponse;
import com.gestor.dominator.dto.room.RoomResponse;
import com.gestor.dominator.dto.tag.TagResponse;

@Builder
@Schema(description = "Product response DTO", example = """
{
  "id": "507f1f77bcf86cd799439011",
  "title": "Modern Chair",
  "category": {
    "id": "507f1f77bcf86cd799439012",
    "name": "Living Room"
  },
  "description": "A comfortable modern chair",
  "images": [{
    "id": "507f1f77bcf86cd799439013",
    "filename": "chair.jpg",
    "url": "http://example.com/images/chair.jpg"
  }],
  "tags": [{
    "id": "507f1f77bcf86cd799439014",
    "name": "Modern"
  }],
  "dimensions": "30x30x40 cm",
  "color": "Black",
  "price": 299.99,
  "createdAt": "2024-01-15T10:30:00",
  "updatedAt": "2024-01-15T10:30:00",
  "version": 1
}
""")
public record ProductResponse(
    @Schema(description = "Product ID", example = "507f1f77bcf86cd799439011")
    String id,
    @Schema(description = "Product title", example = "Modern Chair")
    String title,
    @Schema(description = "Product category/room")
    RoomResponse category,
    @Schema(description = "Product description", example = "A comfortable modern chair")
    String description,
    @Schema(description = "List of product images")
    List<ImageResponse> images,
    @Schema(description = "List of product tags")
    List<TagResponse> tags,
    @Schema(description = "Product dimensions", example = "30x30x40 cm")
    String dimensions,
    @Schema(description = "Product color", example = "Black")
    String color,
    @Schema(description = "Product price", example = "299.99")
    Double price,
    @Schema(description = "Creation timestamp", example = "2024-01-15T10:30:00")
    LocalDateTime createdAt,
    @Schema(description = "Last update timestamp", example = "2024-01-15T10:30:00")
    LocalDateTime updatedAt,
    @Schema(description = "Version number", example = "1")
    int version
) {
}