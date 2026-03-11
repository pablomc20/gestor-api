package com.gestor.dominator.dto.projects.usecase;

import java.util.UUID;

import lombok.Builder;

@Builder
public record DetailsByIdRecord(
        UUID projectId) {

}
