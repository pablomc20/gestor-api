package com.gestor.dominator.model.postgre.projectuser;

import com.gestor.dominator.constants.ProjectUserType;
import lombok.Builder;

@Builder
public record ProjectUserRq(
        String employeeId,
        ProjectUserType type) {
}
