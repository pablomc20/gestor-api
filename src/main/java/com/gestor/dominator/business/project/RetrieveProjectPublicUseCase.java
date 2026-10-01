package com.gestor.dominator.business.project;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.gestor.dominator.dto.projects.usecase.ProjectListPublicResult;
import com.gestor.dominator.mapper.ProjectMapper;
import com.gestor.dominator.repository.project.ProjectRepository;

import lombok.RequiredArgsConstructor;

/**
 * This class is responsible for retrieving public project information.
 * It can be extended to include methods that fetch project details, images, and other public data.
 */
@Service
@RequiredArgsConstructor
public class RetrieveProjectPublicUseCase {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    public List<ProjectListPublicResult> execute() {

        List<ProjectListPublicResult> projectList = projectMapper.toProjectListPublicResult(projectRepository.getProjectListPublic());

        // Placeholder implementation
        return projectList.isEmpty() ? null : projectList;
    }

}
