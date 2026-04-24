package com.gestor.dominator.repository.projectuser;

import java.util.List;

import com.gestor.dominator.model.postgre.projectuser.ProjectUserRq;
import com.gestor.dominator.model.postgre.projectuser.ProjectUserRs;

public interface ProjectUserRepository {

    List<ProjectUserRs> findProjectsByEmployeeAndType(ProjectUserRq request);
}
