package com.gestor.dominator.model.postgre.projects;

import lombok.Builder;

@Builder
public record CreateProjectResult(
        String status,
        String project_id,
        String error_code,
        String error_message) {

}
