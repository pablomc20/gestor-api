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


@RestController
@RequestMapping("/projects/images")
public class ProjectImageController {
  private final ProjectImageService projectImageService;

  public ProjectImageController(ProjectImageService projectImageService) {
    this.projectImageService = projectImageService;
  }

  @PostMapping
  public ResponseEntity<List<ProjectImagesResult>> getProjectImages(@RequestBody ProjectImagesRecord projectImagesRecord) {
    return ResponseEntity.ok(projectImageService.getProjectImages(projectImagesRecord));
  }
}
