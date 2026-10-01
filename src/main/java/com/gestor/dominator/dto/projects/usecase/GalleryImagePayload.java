package com.gestor.dominator.dto.projects.usecase;

/**
 * GalleryImagePayload
 */
public record GalleryImagePayload(
    String id,
    String filePath,
    String medPath,
    String thumbPath,
    String size,
    String mimeType
) {

}
