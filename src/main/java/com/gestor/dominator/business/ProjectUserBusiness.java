package com.gestor.dominator.business;

import java.util.List;

import com.gestor.dominator.constants.ProjectUserType;
import com.gestor.dominator.dto.projectuser.ProjectUserRecord;
import com.gestor.dominator.dto.projectuser.ProjectUserResult;
import com.gestor.dominator.exceptions.custom.DataValidationException;
import com.gestor.dominator.mapper.ProjectMapper;
import com.gestor.dominator.model.postgre.projectuser.ProjectUserRq;
import com.gestor.dominator.repository.projectuser.ProjectUserRepository;
import com.gestor.dominator.service.projectuser.ProjectUserService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectUserBusiness implements ProjectUserService {

    private final ProjectUserRepository projectUserRepository;
    private final ProjectMapper projectMapper;

    @Override
    public List<ProjectUserResult> getProjectsForUser(ProjectUserRecord request) {
        if (request.employeeId() == null || request.employeeId().isBlank()) {
            throw DataValidationException.fieldRequired("employeeId");
        }

        if (request.type() == null || request.type().isBlank()) {
            throw DataValidationException.fieldRequired("type");
        }

        ProjectUserType type;
        try {
            type = ProjectUserType.fromValue(request.type());
        } catch (IllegalArgumentException ex) {
            throw DataValidationException.invalidValue("type", request.type());
        }

        ProjectUserRq projectUserRq = new ProjectUserRq(request.employeeId(), type);
        return projectMapper.toProjectUserResult(projectUserRepository.findProjectsByEmployeeAndType(projectUserRq));
    }
}
