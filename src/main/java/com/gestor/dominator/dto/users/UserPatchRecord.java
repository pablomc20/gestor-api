package com.gestor.dominator.dto.users;

import lombok.Builder;

// DTO para actualizar un usuario, cuando se registre un usuario con info existente

@Builder
public record UserPatchRecord(
        String email,
        String phone,
        String password,
        String legalRepresentative) {
}
