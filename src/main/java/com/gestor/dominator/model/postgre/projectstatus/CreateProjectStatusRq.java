package com.gestor.dominator.model.postgre.projectstatus;

import java.util.UUID;

public record CreateProjectStatusRq(
    UUID projectId,
    String currentStatus,
    UUID byUserId
) {

}
