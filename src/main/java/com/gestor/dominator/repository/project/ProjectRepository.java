package com.gestor.dominator.repository.project;

import java.util.List;
import java.util.UUID;

import com.gestor.dominator.constants.StatusProject;
import com.gestor.dominator.model.postgre.project.CreateProjectRq;
import com.gestor.dominator.model.postgre.project.CreateProjectRs;
import com.gestor.dominator.model.postgre.project.DetailsByIdRs;
import com.gestor.dominator.model.postgre.project.DetailsByIdRq;
import com.gestor.dominator.model.postgre.project.ProjectDetailsRq;
import com.gestor.dominator.model.postgre.project.ProjectDetailsRs;

public interface ProjectRepository {
    List<DetailsByIdRs> getProyectClientById(DetailsByIdRq detailsForClientRq);

    CreateProjectRs createProject(CreateProjectRq createProjectRecord);

    ProjectDetailsRs getProjectDetailsById(ProjectDetailsRq projectDetailsRq);

    String getStatusById(UUID idProject);

    boolean updateStatusProject(UUID idProject, String status);
}
