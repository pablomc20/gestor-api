package com.gestor.dominator.dto.image;

import lombok.Builder;

@Builder
public record ImageRenderResponse(
    byte[] imageData,
    String contentType
) {
}
