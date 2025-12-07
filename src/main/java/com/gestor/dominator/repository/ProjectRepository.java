package com.gestor.dominator.repository;

import java.util.List;

import com.gestor.dominator.model.postgre.project.CreateProjectRq;
import com.gestor.dominator.model.postgre.project.CreateProjectRs;
import com.gestor.dominator.model.postgre.project.DetailsForEmployeeRq;
import com.gestor.dominator.model.postgre.project.DetailsForEmployeeRs;

public interface ProjectRepository {
  List<DetailsForEmployeeRs> getProyectClientById(DetailsForEmployeeRq detailsForClientRq);

  CreateProjectRs createProject(CreateProjectRq createProjectRecord);
}
