package com.gestor.dominator.service.projects;

import java.util.List;

import com.gestor.dominator.model.postgre.project.CreateProjectRq;
import com.gestor.dominator.model.postgre.project.CreateProjectRs;
import com.gestor.dominator.model.postgre.project.DetailsByIdRq;
import com.gestor.dominator.model.postgre.project.DetailsByIdRs;

public interface ProjectDbService {

    CreateProjectRs createProject(CreateProjectRq createProjectRecord);

    List<DetailsByIdRs> getProyectClientById(DetailsByIdRq detailsForClientRq);

    String getStatusById(String idProject);

    void updateStatus(String projectId, String nextStatus);

}

