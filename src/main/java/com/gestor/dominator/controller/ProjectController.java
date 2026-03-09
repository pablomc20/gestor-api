package com.gestor.dominator.controller;

import com.gestor.dominator.business.project.ChangeStatusProjectUseCase;
import com.gestor.dominator.business.project.RetrieveProjectDetailsUseCase;
import com.gestor.dominator.dto.projects.ChangeStatusProjectRecord;
import com.gestor.dominator.dto.projects.CreateProjectRecord;
import com.gestor.dominator.dto.projects.CreateProjectResult;
import com.gestor.dominator.dto.projects.DetailsForEmployeeRecord;
import com.gestor.dominator.dto.projects.DetailsForEmployeeResult;
import com.gestor.dominator.dto.projects.ProjectDetailsRecord;
import com.gestor.dominator.dto.projects.ProjectDetailsResult;
import com.gestor.dominator.dto.projects.StatusProjectRecord;
import com.gestor.dominator.dto.projects.StatusProjectResult;
import com.gestor.dominator.service.projects.ProjectService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/projects")
@Tag(name = "Projects", description = "API para gestión de proyectos")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;
    private final RetrieveProjectDetailsUseCase createProjectUseCase;
    private final ChangeStatusProjectUseCase changeStatusProjectUseCase;

    @GetMapping("/{projectId}/details-client")
    public List<DetailsForEmployeeResult> getDetailsProjectClient(
            DetailsForEmployeeRecord detailsForClientRq) {
        return projectService.getProyectClientById(detailsForClientRq);
    }

    @GetMapping("/{projectId}/details")
    public ResponseEntity<ProjectDetailsResult> getDetailsProjectClient(
            ProjectDetailsRecord projectDetailsRecord) {
        return ResponseEntity.ok(createProjectUseCase.execute(projectDetailsRecord));
    }

    @GetMapping("/status/{projectId}")
    public ResponseEntity<StatusProjectResult> getStatusName(StatusProjectRecord statusProjectRecord) {
        return ResponseEntity.ok(projectService.retrieveStatusById(statusProjectRecord));
    }

    @PostMapping("/status")
    public ResponseEntity<Boolean> changeStatusProject(@RequestBody ChangeStatusProjectRecord changeStatusProjectRecord) {
        changeStatusProjectUseCase.execute(changeStatusProjectRecord);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<CreateProjectResult> createNewProject(@RequestBody CreateProjectRecord createProject) {
        return ResponseEntity.ok(projectService.createNewProject(createProject));
    }
}
