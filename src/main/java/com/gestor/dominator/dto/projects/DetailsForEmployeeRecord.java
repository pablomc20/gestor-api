package com.gestor.dominator.dto.projects;

import java.util.UUID;

import lombok.Builder;

@Builder
public record DetailsForEmployeeRecord(
                UUID projectId) {

}
