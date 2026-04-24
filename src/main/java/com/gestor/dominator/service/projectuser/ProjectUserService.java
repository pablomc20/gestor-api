package com.gestor.dominator.service.projectuser;

import java.util.List;

import com.gestor.dominator.dto.projectuser.ProjectUserRecord;
import com.gestor.dominator.dto.projectuser.ProjectUserResult;

public interface ProjectUserService {

    List<ProjectUserResult> getProjectsForUser(ProjectUserRecord request);
}
