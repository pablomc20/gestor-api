package com.gestor.dominator.dto.auth;

import lombok.Builder;

@Builder
public record AuthResponse(
    String id,
    String name,
    String email,
    String token
) {
}