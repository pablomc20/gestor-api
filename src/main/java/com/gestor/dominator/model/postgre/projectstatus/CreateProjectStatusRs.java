package com.gestor.dominator.model.postgre.projectstatus;

import java.util.UUID;

import lombok.Builder;

@Builder
public record CreateProjectStatusRs(
  UUID statusLogId
) {

}
