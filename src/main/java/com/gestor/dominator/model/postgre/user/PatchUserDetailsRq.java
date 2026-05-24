package com.gestor.dominator.model.postgre.user;

import lombok.Builder;

@Builder
public record PatchUserDetailsRq(
        String userId,
        String phone,
        String legalRepresentative) {

}
