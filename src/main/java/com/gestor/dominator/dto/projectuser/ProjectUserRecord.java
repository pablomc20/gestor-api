package com.gestor.dominator.dto.projectuser;

import lombok.Builder;

@Builder
public record ProjectUserRecord(
        String employeeId,
        String type) {
}
