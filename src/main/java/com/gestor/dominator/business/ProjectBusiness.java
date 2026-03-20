package com.gestor.dominator.business;

import com.gestor.dominator.dto.projects.StatusProjectRecord;
import com.gestor.dominator.dto.projects.StatusProjectResult;
import com.gestor.dominator.dto.projects.usecase.DetailsByIdRecord;
import com.gestor.dominator.dto.projects.usecase.DetailsByIdResult;
import com.gestor.dominator.exceptions.custom.DataValidationException;
import com.gestor.dominator.mapper.ProjectMapper;
import com.gestor.dominator.model.postgre.project.DetailsByIdRs;
import com.gestor.dominator.repository.project.ProjectRepository;
import com.gestor.dominator.model.postgre.project.DetailsByIdRq;
import com.gestor.dominator.service.projects.ProjectService;
import com.gestor.dominator.constants.StatusProject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectBusiness implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    @Override
    public List<DetailsByIdResult> getProyectEmployeeById(DetailsByIdRecord detailsForClientRecord) {
        DetailsByIdRq detailsForClientRq = projectMapper.toDetailsRq(detailsForClientRecord);

        List<DetailsByIdRs> detailsForClientRs = projectRepository.getProyectClientById(detailsForClientRq);

        return projectMapper.toDetailsRs(detailsForClientRs);
    }

    @Override
    public StatusProjectResult retrieveStatusById(StatusProjectRecord statusProjectRecord) {
        UUID idProject = UUID.fromString(statusProjectRecord.projectId());
        String currentStatus = projectRepository.getStatusById(idProject);

        if (currentStatus.isEmpty()) {
            throw new DataValidationException("No se pudo obtneer el estado del proyecto");
        }

        String nextStatus = StatusProject.retrieveNextStatusProject(currentStatus);

        return projectMapper.toStatusProjectResult(currentStatus, nextStatus);
    }

}
