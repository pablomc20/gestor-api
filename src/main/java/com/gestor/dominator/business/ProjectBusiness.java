package com.gestor.dominator.business;

import com.gestor.dominator.dto.projects.CreateProjectRq;
import com.gestor.dominator.dto.projects.CreateProjectRs;
import com.gestor.dominator.dto.projects.DetailsForClientRq;
import com.gestor.dominator.dto.projects.DetailsForClientRs;
import com.gestor.dominator.exceptions.custom.PostgreDbException;
import com.gestor.dominator.model.postgre.projects.CreateProjectRecord;
import com.gestor.dominator.model.postgre.projects.CreateProjectResult;
import com.gestor.dominator.repository.ProjectRepository;
import com.gestor.dominator.service.projects.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjectBusiness implements ProjectService {

  private final ProjectRepository projectRepository;

  // @Override
  // public Optional<DetailsForClientRs> getProyectClientById(DetailsForClientRq
  // detailsForClientRq) {
  // return Optional.empty();
  // }

  @Override
  public CreateProjectRs createNewProject(CreateProjectRq createProject) {
    CreateProjectRecord createProjectRecord = CreateProjectRecord.builder()
        .title(createProject.title())
        .description(createProject.description())
        .size(createProject.size())
        .startDate(createProject.startDate())
        .estimatedCompletionDate(createProject.estimatedCompletionDate())
        .budget(createProject.budget())
        .category(createProject.category())
        .client(createProject.client())
        .employee(createProject.employee())
        .colors(createProject.colors())
        .materials(createProject.materials())
        .images(createProject.images())
        .build();
    CreateProjectResult createProjectResult = projectRepository.createProject(createProjectRecord);

    if (createProjectResult.status().equals("ok")) {
      return CreateProjectRs.builder().idProject(createProjectResult.project_id()).build();
    }
    throw new PostgreDbException("Error al crear un nuevo proyecto");
  }
}
