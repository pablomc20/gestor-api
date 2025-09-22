package com.gestor.dominator.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProductRequest {
    private String title;
    private String category;
    private String description;
    private List<String> images;
    private List<String> tags;
    private String dimensions;
    private String color;
    private Double price;
}