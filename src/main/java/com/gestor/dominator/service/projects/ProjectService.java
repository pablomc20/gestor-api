package com.gestor.dominator.service.projects;

import java.util.List;

import com.gestor.dominator.dto.projects.CreateProjectRq;
import com.gestor.dominator.dto.projects.CreateProjectRs;
import com.gestor.dominator.dto.projects.DetailsForClientRq;
import com.gestor.dominator.dto.projects.DetailsForClientRs;

public interface ProjectService {

  List<DetailsForClientRs> getProyectClientById(DetailsForClientRq detailsForClientRq);

  CreateProjectRs createNewProject(CreateProjectRq createProject);
}
