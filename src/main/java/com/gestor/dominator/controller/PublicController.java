package com.gestor.dominator.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gestor.dominator.dto.image.ImageRenderResult;
import com.gestor.dominator.dto.projects.ProjectDetailsRecord;
import com.gestor.dominator.dto.projects.ProjectDetailsResult;
import com.gestor.dominator.service.image.ImageService;
import com.gestor.dominator.service.projects.ProjectService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "/public")
@RequiredArgsConstructor
public class PublicController {

    private final ProjectService projectService;

    @GetMapping("/project/{id}")
    public ResponseEntity<ProjectDetailsResult> getProjectDetailsById(@PathVariable String id) {
        ProjectDetailsRecord projectDetailsRecord = new ProjectDetailsRecord(UUID.fromString(id));
        ProjectDetailsResult result = projectService.getProjectDetailsById(projectDetailsRecord);
        return ResponseEntity.ok(result);
    }

    // @GetMapping("/file/{filename}")
    // public ResponseEntity<byte[]> getImageFile(@PathVariable String filename) {
    // ImageRenderResponse response = imageService.getImageFile(filename);

    // return ResponseEntity.ok()
    // .contentType(MediaType.parseMediaType(response.contentType()))
    // .body(response.imageData());
    // }
}