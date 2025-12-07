package com.gestor.dominator.repository;

import com.gestor.dominator.model.postgre.projects.CreateProjectRecord;
import com.gestor.dominator.model.postgre.projects.CreateProjectResult;

public interface ProjectRepository {
  CreateProjectResult createProject(CreateProjectRecord createProjectRecord);
}
