package com.gestor.dominator.dto.category;

import lombok.Builder;

@Builder
public record CategoryResult(
        String idCategory,
        String name,
        String slug) {
}
