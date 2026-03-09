package com.gestor.dominator.dto.projects;

import lombok.Builder;

@Builder
public record StatusProjectResult(
        String currentStatus,
        String nextStatus) {

}
