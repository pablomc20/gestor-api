package com.gestor.dominator.service.projects;

import com.gestor.dominator.dto.projects.CreateProjectRq;
import com.gestor.dominator.dto.projects.CreateProjectRs;
import com.gestor.dominator.dto.projects.DetailsForClientRq;
import com.gestor.dominator.dto.projects.DetailsForClientRs;

import java.util.Optional;

public interface ProjectService {

  // Optional<DetailsForClientRs> getProyectClientById(DetailsForClientRq
  // detailsForClientRq);

  CreateProjectRs createNewProject(CreateProjectRq createProject);
}
