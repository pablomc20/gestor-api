package com.gestor.dominator.dto.users;

import lombok.Builder;

@Builder
public record UserDetailsResult(
        String email,
        String phone,
        String legalRepresentative,
        String taxId) {

}
