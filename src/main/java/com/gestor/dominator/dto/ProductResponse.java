package com.gestor.dominator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import com.gestor.dominator.dto.tag.TagResponse;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
    private String id;
    private String title;
    private RoomResponse category;
    private String description;
    private List<ImageResponse> images;
    private List<TagResponse> tags;
    private String dimensions;
    private String color;
    private Double price;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int version;
}