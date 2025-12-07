package com.gestor.dominator.repository;

import java.util.List;

import com.gestor.dominator.dto.projects.DetailsForClientRq;
import com.gestor.dominator.dto.projects.DetailsForClientRs;
import com.gestor.dominator.model.postgre.project.CreateProjectRecord;
import com.gestor.dominator.model.postgre.project.CreateProjectResult;

public interface ProjectRepository {
  List<DetailsForClientRs> getProyectClientById(DetailsForClientRq detailsForClientRq);

  CreateProjectResult createProject(CreateProjectRecord createProjectRecord);
}
