package com.gestor.dominator.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "tags")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class Tag {

    @Id
    private String id;
    private String name;
    private String slug;
    private String type;
    private String group; // Reference to another entity
    private int version;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Tag(String name, String slug, String type, String group) {
        this.name = name;
        this.slug = slug;
        this.type = type;
        this.group = group;
        this.version = 0;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}