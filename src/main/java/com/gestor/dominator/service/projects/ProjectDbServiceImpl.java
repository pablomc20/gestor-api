package com.gestor.dominator.service.projects;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.gestor.dominator.exceptions.custom.PostgreDbException;
import com.gestor.dominator.model.postgre.project.CreateProjectRq;
import com.gestor.dominator.model.postgre.project.CreateProjectRs;
import com.gestor.dominator.model.postgre.project.DetailsByIdRq;
import com.gestor.dominator.model.postgre.project.DetailsByIdRs;
import com.gestor.dominator.repository.project.ProjectRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectDbServiceImpl implements ProjectDbService {

    private final ProjectRepository projectRepository;

    @Override
    public CreateProjectRs createProject(CreateProjectRq createProjectRecord) {
        return projectRepository.createProject(createProjectRecord);
    }

    @Override
    public List<DetailsByIdRs> getProyectClientById(DetailsByIdRq detailsForClientRq) {
        return projectRepository.getProyectClientById(detailsForClientRq);
    }

    @Override
    public String getStatusById(String idProject) {
        UUID projectUuid = UUID.fromString(idProject);
        return projectRepository.getStatusById(projectUuid);
    }

    @Override
    public void updateStatus(String projectId, String nextStatus) {
        UUID projectUuid = UUID.fromString(projectId);
        boolean isUpdated = projectRepository.updateStatusProject(projectUuid, nextStatus);
        if (!isUpdated) {
            throw new PostgreDbException("Falló al cambiar el estatus");
        }
    }
}

