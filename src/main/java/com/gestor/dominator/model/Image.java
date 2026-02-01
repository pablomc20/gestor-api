package com.gestor.dominator.model;

import org.springframework.data.annotation.Id;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
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