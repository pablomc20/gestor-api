package com.gestor.dominator.model.postgre.project;

import lombok.Builder;

/**
 * GalleryImageDto
 */
@Builder
public record GalleryImageDto(
    String id,
    String filename,
    String size,
    String mimeType,
    String ext
) {

}
