package com.gestor.dominator.controller;

import com.gestor.dominator.dto.image.ImageRenderResponse;
import com.gestor.dominator.dto.image.ImageResponse;
import com.gestor.dominator.dto.image.ImageUpdateRequest;
import com.gestor.dominator.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
        imageService.deleteImage(id);
        return ResponseEntity.noContent().build();
    }

}
