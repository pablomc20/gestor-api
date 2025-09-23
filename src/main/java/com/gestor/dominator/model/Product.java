package com.gestor.dominator.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "products")
@Builder
public record Product(
    @Id
    String id,
    String title,
    ObjectId category,
    String description,
    List<ObjectId> images,
    List<ObjectId> tags,
    String dimensions,
    String color,
    Double price,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    int version
) {

}