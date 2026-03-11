package com.gestor.dominator.controller;

import com.gestor.dominator.business.project.ChangeStatusProjectUseCase;
import com.gestor.dominator.business.project.CreateProjectUseCase;
import com.gestor.dominator.business.project.RetrieveProjectDetailsUseCase;
import com.gestor.dominator.dto.projects.CreateProjectRecord;
import com.gestor.dominator.dto.projects.CreateProjectResult;
import com.gestor.dominator.dto.projects.ProjectDetailsRecord;
import com.gestor.dominator.dto.projects.ProjectDetailsResult;
import com.gestor.dominator.dto.projects.StatusProjectRecord;
import com.gestor.dominator.dto.projects.StatusProjectResult;
import com.gestor.dominator.dto.projects.usecase.ChangeStatusProjectRecord;
import com.gestor.dominator.dto.projects.usecase.DetailsByIdRecord;
import com.gestor.dominator.dto.projects.usecase.DetailsByIdResult;
import com.gestor.dominator.service.projects.ProjectService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
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
    private final RetrieveProjectDetailsUseCase retrieveProjectDetailsUseCase;
    private final CreateProjectUseCase createProjectUseCase;
    private final ChangeStatusProjectUseCase changeStatusProjectUseCase;

    @GetMapping("/{projectId}/details-client")
    public List<DetailsByIdResult> getDetailsProjectClient(
            DetailsByIdRecord detailsForClientRq) {
        return projectService.getProyectEmployeeById(detailsForClientRq);
    }

    @GetMapping("/{projectId}/details")
    public ResponseEntity<ProjectDetailsResult> getDetailsProjectClient(
            ProjectDetailsRecord projectDetailsRecord) {
        return ResponseEntity.ok(retrieveProjectDetailsUseCase.execute(projectDetailsRecord));
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
    @Validated
    public ResponseEntity<CreateProjectResult> createNewProject(@Valid @RequestBody CreateProjectRecord createProject) {
        return ResponseEntity.ok(createProjectUseCase.execute(createProject));
    }
}
