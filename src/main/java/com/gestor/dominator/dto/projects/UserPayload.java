package com.gestor.dominator.dto.projects;

import lombok.Builder;

@Builder
public record UserPayload(
        String userId,
        String email,
        String phone,
        String legalRepresentative) {

}
