package com.gestor.dominator.dto.room;

import lombok.Builder;
import java.time.LocalDateTime;

@Builder
public record RoomResponse(
    String id,
    String name,
    String slug,
    int version,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
    
}
