package com.gestor.dominator.business;

import java.util.List;

import com.gestor.dominator.constants.ProjectUserType;
import com.gestor.dominator.constants.UserType;
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
    public List<ProjectUserResult> getProjectsForEmployee(ProjectUserRecord request) {
        return getProjectsForUser(request, UserType.EMPLOYEE);
    }

    @Override
    public List<ProjectUserResult> getProjectsForClient(ProjectUserRecord request) {
        return getProjectsForUser(request, UserType.CLIENT);
    }

    List<ProjectUserResult> getProjectsForUser(ProjectUserRecord request, UserType userType) {
        if (request.userId() == null || request.userId().isBlank()) {
            throw DataValidationException.fieldRequired("userId");
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

        ProjectUserRq projectUserRq = new ProjectUserRq(request.userId(), type);

        if (userType == UserType.EMPLOYEE) {
            return projectMapper.toProjectUserResult(projectUserRepository.findProjectsByEmployeeAndType(projectUserRq));
        } else if (userType == UserType.CLIENT) {
            return projectMapper.toProjectUserResult(projectUserRepository.findProjectsByClientAndType(projectUserRq));
        } else {
            throw new IllegalArgumentException("Unsupported user type: " + userType);
        }

    }
    
}
