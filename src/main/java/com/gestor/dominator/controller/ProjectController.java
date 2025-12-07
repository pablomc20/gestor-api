package com.gestor.dominator.controller;

import com.gestor.dominator.dto.ErrorResponse;
import com.gestor.dominator.dto.projects.CreateProjectRq;
import com.gestor.dominator.dto.projects.CreateProjectRs;
import com.gestor.dominator.dto.projects.DetailsForClientRq;
import com.gestor.dominator.dto.projects.DetailsForClientRs;
import com.gestor.dominator.service.projects.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

  @GetMapping("/{projectId}/details-client")
  @Operation(summary = "Obtener detalles del proyecto para cliente", description = "Recupera los detalles completos de un proyecto específico para mostrar al cliente")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Detalles del proyecto encontrados", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DetailsForClientRs.class))),
      @ApiResponse(responseCode = "404", description = "Proyecto no encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
      @ApiResponse(responseCode = "400", description = "ID de proyecto inválido", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
  })
  public List<DetailsForClientRs> getDetailsProjectClient(
      DetailsForClientRq detailsForClientRq) {
    return projectService.getProyectClientById(detailsForClientRq);
  }

  @PostMapping("/new")
  public ResponseEntity<CreateProjectRs> createNewProject(@RequestBody CreateProjectRq createProject) {
    return ResponseEntity.ok(projectService.createNewProject(createProject));
  }
}
