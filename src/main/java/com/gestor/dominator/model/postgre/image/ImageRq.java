package com.gestor.dominator.model.postgre.image;

import java.time.OffsetDateTime;

import lombok.Builder;

@Builder
public record ImageRq(
        String id,
        String filename,
        long size,
        String mimeType,
        String ext,
        OffsetDateTime createdAt) {
}
