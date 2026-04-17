package com.gestor.dominator.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gestor.dominator.dto.projectimage.ProjectImagesRecord;
import com.gestor.dominator.dto.projectimage.ProjectImagesResult;
import com.gestor.dominator.service.projectimage.ProjectImageService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/projects/images")
@RequiredArgsConstructor
public class ProjectImageController {
  private final ProjectImageService projectImageService;

  @PostMapping
  public ResponseEntity<List<ProjectImagesResult>> getProjectImages(@RequestBody ProjectImagesRecord projectImagesRecord) {
    return ResponseEntity.ok(projectImageService.getProjectImages(projectImagesRecord));
  }
}
