package com.gestor.dominator.repository.project;

import java.util.List;

import com.gestor.dominator.model.postgre.project.CreateProjectRq;
import com.gestor.dominator.model.postgre.project.CreateProjectRs;
import com.gestor.dominator.model.postgre.project.DetailsByIdRs;
import com.gestor.dominator.model.postgre.project.DetailsByIdRq;
import com.gestor.dominator.model.postgre.project.ProjectDetailsRq;
import com.gestor.dominator.model.postgre.project.ProjectDetailsRs;
import com.gestor.dominator.model.postgre.project.ProjectListPublicRs;

public interface ProjectRepository {
    List<DetailsByIdRs> getProyectClientById(DetailsByIdRq detailsForClientRq);

    CreateProjectRs createProject(CreateProjectRq createProjectRecord);

    ProjectDetailsRs getProjectDetailsById(ProjectDetailsRq projectDetailsRq);

    List<ProjectListPublicRs> getProjectListPublic();

}
