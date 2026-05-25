package com.gestor.dominator.dto.users;

import jakarta.validation.constraints.Email;
import lombok.Builder;

@Builder
public record UserRecord(
        @Email String email,
        String phone,
        String legalRepresentative,
        String taxId) {
}
