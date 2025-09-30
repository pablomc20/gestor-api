package com.gestor.dominator.dto.image;

import lombok.Builder;

import java.time.LocalDateTime;

import com.gestor.dominator.model.Image;

@Builder
public record ImageResponse(
    String id,
    String filePath,
    String medPath,
    String thumbPath,
    long size,
    String mimeType,
    LocalDateTime createdAt
) {
    public static ImageResponse fromModel(Image image) {
        return new ImageResponse(
            image.getId().toString(),
            "/public/file/" + image.getFilename() + "_orig" + image.getExt(),
            "/public/file/" + image.getFilename() + "_med" + image.getExt(),
            "/public/file/" + image.getFilename() + "_thumb" + image.getExt(),
            image.getSize(),
            image.getMimeType(),
            image.getCreatedAt()
        );
    }
}

