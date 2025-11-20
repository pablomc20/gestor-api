package com.gestor.dominator.model.postgre;

import java.time.LocalDateTime;
import java.util.UUID;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String filename;
    private String originalName;
    private long size;
    private String mimeType;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
