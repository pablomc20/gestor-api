package com.gestor.dominator.controller;

import com.gestor.dominator.dto.image.ImageCreateResult;
import com.gestor.dominator.dto.image.ImageRenderResult;
import com.gestor.dominator.dto.image.ImageResult;
import com.gestor.dominator.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/images")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @GetMapping("/{id}")
    public ResponseEntity<ImageResult> getImageById(@PathVariable String id) {
        ImageResult image = imageService.getImageById(id);
        return ResponseEntity.ok(image);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ImageCreateResult> uploadImage(@RequestParam("file") MultipartFile file) {
        ImageCreateResult response = imageService.uploadImage(file);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteImage(@PathVariable String id) {
        imageService.deleteImage(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/file/{filename}")
    public ResponseEntity<byte[]> getImageFile(@PathVariable String filename) {
        ImageRenderResult response = imageService.getImageFile(filename);

        return ResponseEntity.ok()
                .contentType(response.mediaType())
                .body(response.imageData());
    }

}
