package com.gestor.dominator.dto.image;

import org.springframework.http.MediaType;

import lombok.Builder;

@Builder
public record ImageRenderResult(
        byte[] imageData,
        MediaType mediaType) {
}
