package com.gestor.dominator.model.client.minio;

import lombok.Builder;

@Builder
public record ImageRenderResponse(
        byte[] imageData,
        String contentType) {
}
