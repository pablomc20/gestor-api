package com.gestor.dominator.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "products")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class Product {

    @Id
    private String id;
    private String title;
    private String category; // Reference to Room/Category ID
    private String description;
    private List<String> images;
    private List<String> tags; // References to Tag IDs
    private String dimensions;
    private String color;
    private Double price;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int version;

    public Product(String title, String category, String description, List<String> images,
                   List<String> tags, String dimensions, String color, Double price) {
        this.title = title;
        this.category = category;
        this.description = description;
        this.images = images;
        this.tags = tags;
        this.dimensions = dimensions;
        this.color = color;
        this.price = price;
        this.version = 0;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}