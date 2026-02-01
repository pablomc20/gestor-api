package com.gestor.dominator.repository.project;

import java.util.List;

import com.gestor.dominator.dto.projects.ProjectPayload;
import com.gestor.dominator.model.postgre.project.CreateProjectRq;
import com.gestor.dominator.model.postgre.project.CreateProjectRs;
import com.gestor.dominator.model.postgre.project.DetailsForClientRs;
import com.gestor.dominator.model.postgre.project.DetailsForEmployeeRq;
import com.gestor.dominator.model.postgre.project.ProjectDetailsRq;
import com.gestor.dominator.model.postgre.project.ProjectDetailsRs;

public interface ProjectRepository {
    List<DetailsForClientRs> getProyectClientById(DetailsForEmployeeRq detailsForClientRq);

    CreateProjectRs createProject(CreateProjectRq createProjectRecord);

    ProjectDetailsRs getProjectDetailsById(ProjectDetailsRq projectDetailsRq);
}
