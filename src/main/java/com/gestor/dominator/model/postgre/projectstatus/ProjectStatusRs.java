package com.gestor.dominator.model.postgre.projectstatus;

import java.time.LocalDateTime;

public record ProjectStatusRs(
    String currentStatus,
    LocalDateTime dateChanged
) {

}
