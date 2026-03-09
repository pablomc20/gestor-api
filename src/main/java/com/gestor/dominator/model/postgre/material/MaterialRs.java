package com.gestor.dominator.model.postgre.material;

import lombok.Builder;

@Builder
public record MaterialRs(
        String material_id,
        String name,
        String slug) {
}
