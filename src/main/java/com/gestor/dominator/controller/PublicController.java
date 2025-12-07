package com.gestor.dominator.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gestor.dominator.dto.image.ImageRenderResponse;
import com.gestor.dominator.service.ImageService;

// @RestController
// @RequestMapping(path = "/public")
public class PublicController {

    // @Autowired
    // private ImageService imageService;

    // @GetMapping("/file/{filename}")
    // public ResponseEntity<byte[]> getImageFile(@PathVariable String filename) {
    // ImageRenderResponse response = imageService.getImageFile(filename);

    // return ResponseEntity.ok()
    // .contentType(MediaType.parseMediaType(response.contentType()))
    // .body(response.imageData());
    // }
}