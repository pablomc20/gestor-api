package com.gestor.dominator.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "images")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class Image {

    @Id
    private String id;
    private String filename;
    private String originalName;
    private long size;
    private String mimeType;
    private LocalDateTime createdAt;

    public Image(String filename, String originalName, long size, String mimeType) {
        this.filename = filename;
        this.originalName = originalName;
        this.size = size;
        this.mimeType = mimeType;
        this.createdAt = LocalDateTime.now();
    }
}