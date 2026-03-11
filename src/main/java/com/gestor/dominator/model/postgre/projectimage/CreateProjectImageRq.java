package com.gestor.dominator.model.postgre.projectimage;

import java.util.UUID;

import lombok.Builder;

@Builder
public record CreateProjectImageRq(
        UUID projectId,
        UUID imageId,
        String type,
        String visibility) {

}
