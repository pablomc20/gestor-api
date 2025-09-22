package com.gestor.dominator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageResponse {
    private String id;
    private String filename;
    private String originalName;
    private long size;
    private String mimeType;
    private LocalDateTime createdAt;
}