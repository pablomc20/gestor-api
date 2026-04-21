package com.gestor.dominator.dto.chapes;

import lombok.Builder;

@Builder
public record ChapeResult(
        String idChape,
        String name) {
}
