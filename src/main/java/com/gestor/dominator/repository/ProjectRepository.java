package com.gestor.dominator.repository;

import com.gestor.dominator.model.postgre.project.CreateProjectRecord;
import com.gestor.dominator.model.postgre.project.CreateProjectResult;

public interface ProjectRepository {
  CreateProjectResult createProject(CreateProjectRecord createProjectRecord);
}
