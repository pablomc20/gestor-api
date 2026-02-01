package com.gestor.dominator.model.postgre.project;

import java.util.UUID;

import lombok.Builder;

@Builder
public record ProjectDetailsRq(
        UUID projectId) {

}
