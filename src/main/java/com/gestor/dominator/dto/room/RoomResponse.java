package com.gestor.dominator.dto.room;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

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
