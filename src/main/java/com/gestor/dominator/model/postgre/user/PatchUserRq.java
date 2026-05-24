package com.gestor.dominator.model.postgre.user;

import lombok.Builder;

@Builder
public record PatchUserRq(
        String email,
        String password) {

}
