package com.gestor.dominator.dto.projects;

import java.time.LocalDateTime;

import lombok.Builder;

@Builder
public record StatusPayload(
        String currentStatus,
        LocalDateTime dateChanged
) {
  
}
