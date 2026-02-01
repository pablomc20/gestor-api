package com.gestor.dominator.model.postgre.project;

import lombok.Builder;

@Builder
public record CreateProjectRs(
        String project_id) {

}
