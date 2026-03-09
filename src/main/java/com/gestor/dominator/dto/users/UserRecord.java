package com.gestor.dominator.dto.users;

import lombok.Builder;

@Builder
public record UserRecord(
        String email,
        String phone,
        String legalRepresentative,
        String taxId) {
}
