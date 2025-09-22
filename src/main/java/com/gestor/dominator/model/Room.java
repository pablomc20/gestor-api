package com.gestor.dominator.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "category")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class Room {

    @Id
    private String id;
    private String name;
    private String slug;
    private int version;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Room(String name, String slug) {
        this.name = name;
        this.slug = slug;
        this.version = 0;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}