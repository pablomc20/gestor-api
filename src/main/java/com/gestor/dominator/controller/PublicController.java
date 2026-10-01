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

import com.gestor.dominator.business.project.RetrieveProjectDetailsUseCase;
import com.gestor.dominator.business.project.RetrieveProjectPublicUseCase;
import com.gestor.dominator.dto.image.ImageRenderResult;
import com.gestor.dominator.dto.projects.ProjectDetailsRecord;
import com.gestor.dominator.dto.projects.ProjectDetailsResult;
import com.gestor.dominator.dto.projects.usecase.ProjectListPublicResult;
import com.gestor.dominator.service.image.ImageService;
import com.gestor.dominator.service.projects.ProjectService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "/public")
@RequiredArgsConstructor
public class PublicController {

    private final RetrieveProjectDetailsUseCase createProjectUseCase;
    private final RetrieveProjectPublicUseCase retrieveProjectPublicUseCase;

    @GetMapping("/project/{id}")
    public ResponseEntity<ProjectDetailsResult> getProjectDetailsById(@PathVariable String id) {
        ProjectDetailsRecord projectDetailsRecord = new ProjectDetailsRecord(id);
        ProjectDetailsResult result = createProjectUseCase.execute(projectDetailsRecord);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/image")
    public ResponseEntity<byte[]> getImageFile() {
        // Implement the logic to retrieve the image file here
        // For example, you can call a service method to get the image data
        // byte[] imageData = imageService.getImageData();
        // return ResponseEntity.ok()
        //         .contentType(MediaType.IMAGE_JPEG) // or the appropriate media type
        //         .body(imageData);
        
        // return ResponseEntity.ok()
        // .contentType(MediaType.parseMediaType(response.contentType()))
        // .body(response.imageData());

        return ResponseEntity.ok().build(); // Placeholder response{
    }
    
    
    @GetMapping("/projectlist")
    public ResponseEntity<List<ProjectListPublicResult>> getProjectList() {
        // Implement the logic to retrieve the project list here
        // For example, you can call a service method to get the project data
        // byte[] projectData = projectService.getProjectData();
        // return ResponseEntity.ok()
        //         .contentType(MediaType.IMAGE_JPEG) // or the appropriate media type
        //         .body(imageData);
        return ResponseEntity.ok(retrieveProjectPublicUseCase.execute()); // Placeholder response
    }

}