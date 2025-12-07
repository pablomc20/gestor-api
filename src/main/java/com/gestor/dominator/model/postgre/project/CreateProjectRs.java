package com.gestor.dominator.model.postgre.project;

import lombok.Builder;

@Builder
public record CreateProjectRs(
    String status,
    String project_id,
    String error_code,
    String error_message) {

}
