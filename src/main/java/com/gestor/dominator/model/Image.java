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
    private String ext;
    private long size;
    private String mimeType;
    private LocalDateTime createdAt;

    public Image(String filename, String ext, long size, String mimeType) {
        this.filename = filename;
        this.ext = ext;
        this.size = size;
        this.mimeType = mimeType;
        this.createdAt = LocalDateTime.now();
    }
}