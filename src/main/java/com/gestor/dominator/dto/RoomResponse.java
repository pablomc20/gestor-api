package com.gestor.dominator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomResponse {
    private String id;
    private String name;
    private String slug;
    private int version;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}