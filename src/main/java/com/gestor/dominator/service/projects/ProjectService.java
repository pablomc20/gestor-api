package com.gestor.dominator.service.projects;

import java.util.List;

import com.gestor.dominator.dto.projects.CreateProjectRecord;
import com.gestor.dominator.dto.projects.CreateProjectResult;
import com.gestor.dominator.dto.projects.DetailsForEmployeeRecord;
import com.gestor.dominator.dto.projects.DetailsForEmployeeResult;
import com.gestor.dominator.dto.projects.ProjectDetailsRecord;
import com.gestor.dominator.dto.projects.ProjectDetailsResult;

public interface ProjectService {

  List<DetailsForEmployeeResult> getProyectClientById(DetailsForEmployeeRecord detailsForClientRq);

  CreateProjectResult createNewProject(CreateProjectRecord createProject);

  ProjectDetailsResult getProjectDetailsById(ProjectDetailsRecord projectDetailsRecord);
}
