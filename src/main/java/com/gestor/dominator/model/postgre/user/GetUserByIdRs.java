package com.gestor.dominator.model.postgre.user;

import lombok.Builder;

@Builder
public record GetUserByIdRs(
        String email,
        String phone,
        String legal_representative,
        String tax_id) {

}
