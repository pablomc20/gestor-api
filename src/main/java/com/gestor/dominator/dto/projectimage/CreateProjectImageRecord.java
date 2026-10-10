package com.gestor.dominator.dto.projectimage;

import java.util.UUID;

import lombok.Builder;

@Builder
public record CreateProjectImageRecord(
    UUID projectId,
    UUID imageId
) {
    
}
