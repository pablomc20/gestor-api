package com.gestor.dominator.dto.product;

import lombok.Builder;
import java.time.LocalDateTime;
import java.util.List;

import com.gestor.dominator.dto.image.ImageResponse;
import com.gestor.dominator.dto.room.RoomResponse;
import com.gestor.dominator.dto.tag.TagResponse;

@Builder
public record ProductResponse(
    String id,
    String title,
    RoomResponse category,
    String description,
    List<ImageResponse> images,
    List<TagResponse> tags,
    String dimensions,
    String color,
    Double price,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    int version
) {
}