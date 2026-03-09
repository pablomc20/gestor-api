package com.gestor.dominator.model.postgre.user;

import lombok.Builder;

@Builder
public record CreateUserRq(
        String email,
        String phone,
        String legalRepresentative,
        String taxId) {

}
