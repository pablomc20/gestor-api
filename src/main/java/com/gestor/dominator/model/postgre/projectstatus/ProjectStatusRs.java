package com.gestor.dominator.model.postgre.projectstatus;

public record ProjectStatusRs(
    String currentStatus,
    String dateChanged
) {

}
