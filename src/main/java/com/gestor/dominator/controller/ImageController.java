package com.gestor.dominator.controller;

import com.gestor.dominator.dto.ImageResponse;
import com.gestor.dominator.dto.ImageUpdateRequest;
import com.gestor.dominator.exceptions.custom.FileSystemException;
import com.gestor.dominator.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/images")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @GetMapping
    public ResponseEntity<List<ImageResponse>> getAllImages() {
        List<ImageResponse> images = imageService.getAllImages();
        return ResponseEntity.ok(images);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ImageResponse> getImageById(@PathVariable String id) {
        Optional<ImageResponse> image = imageService.getImageById(id);
        return image.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ImageResponse> uploadImage(@RequestParam("file") MultipartFile file) {
        ImageResponse response = imageService.uploadImage(file);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateImage(@PathVariable String id, @RequestBody ImageUpdateRequest request) {
        Optional<ImageResponse> updatedImage = imageService.updateImage(id, request);
        return updatedImage.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteImage(@PathVariable String id) {
        boolean deleted = imageService.deleteImage(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/file/{filename}")
    public ResponseEntity<byte[]> getImageFile(@PathVariable String filename) {
        try {
            Path filePath = Paths.get("uploads/images", filename);
            if (!Files.exists(filePath)) {
                return ResponseEntity.notFound().build();
            }

            byte[] imageBytes = Files.readAllBytes(filePath);
            String contentType = Files.probeContentType(filePath);
            if (contentType == null) {
                contentType = "application/octet-stream";
            }


            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(imageBytes);
        } catch (IOException e) {
            throw new FileSystemException("Error al leer el archivo de imagen", e);
        }
    }
}
