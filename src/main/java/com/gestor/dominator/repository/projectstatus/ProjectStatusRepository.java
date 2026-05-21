package com.gestor.dominator.repository.projectstatus;

import java.util.List;

import com.gestor.dominator.model.postgre.projectstatus.CreateProjectStatusRq;
import com.gestor.dominator.model.postgre.projectstatus.CreateProjectStatusRs;
import com.gestor.dominator.model.postgre.projectstatus.ProjectStatusRq;
import com.gestor.dominator.model.postgre.projectstatus.ProjectStatusRs;

public interface ProjectStatusRepository {
  List<ProjectStatusRs> findProjectStatus(ProjectStatusRq request);

  CreateProjectStatusRs createProjectStatus(CreateProjectStatusRq request);
}
