package com.gestor.dominator.model.client.minio;

import lombok.Builder;

@Builder
public record ImageRenderRs(
        byte[] imageData,
        String contentType) {
}
