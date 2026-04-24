package com.gestor.dominator.controller;

import java.util.List;

import com.gestor.dominator.dto.projectuser.ProjectUserRecord;
import com.gestor.dominator.dto.projectuser.ProjectUserResult;
import com.gestor.dominator.service.projectuser.ProjectUserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/projectusers")
@Tag(name = "ProjectUser", description = "API para consultas de proyectos por tipo del usuario")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class ProjectUserController {

    private final ProjectUserService projectUserService;

    @GetMapping("/employee")
    public ResponseEntity<List<ProjectUserResult>> getProjectsByUserType(
            @RequestParam("employeeId") String employeeId,
            @RequestParam("type") String type) {
        ProjectUserRecord request = new ProjectUserRecord(employeeId, type);
        return ResponseEntity.ok(projectUserService.getProjectsForUser(request));
    }
}
