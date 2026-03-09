package com.gestor.dominator.model.postgre.category;

import lombok.Builder;

@Builder
public record CategoryRs(
        String category_id,
        String name,
        String slug) {
}
