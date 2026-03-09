package com.gestor.dominator.model.postgre.user;

import lombok.Builder;

@Builder
public record CreateUserDetailsRq(
        String userDetailId,
        String userId,
        String name,
        String phone,
        String legalRepresentative,
        String taxId) {

}
