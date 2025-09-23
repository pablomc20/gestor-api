package com.gestor.dominator.dto.image;

import lombok.Builder;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;

@Builder
public record ImageResponse(
    ObjectId id,
    String filename,
    String originalName,
    long size,
    String mimeType,
    LocalDateTime createdAt
) {
}