package com.gestor.dominator.dto.product;

import java.util.List;

import lombok.Builder;

@Builder
public record ProductWithReferencesResponse(
    String id,
    String title,
    Category category,
    String description,
    List<Image> images,
    List<Tag> tags,
    String dimensions,
    String color,
    Double price,
    String createdAt,
    String updatedAt,
    int version
) {

    @Builder
    public record Category(
        String name,
        String slug
    ) {
        
    }
    
    @Builder
    public record Tag(
        String name,
        String slug
    ) {
    }
    
    @Builder
    public record Image(
        String filename,
        String originalName,
        long size   
    ) {
    
    }
}
