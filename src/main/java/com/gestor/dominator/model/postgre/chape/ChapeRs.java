package com.gestor.dominator.model.postgre.chape;

import lombok.Builder;

@Builder
public record ChapeRs(
        String chape_id,
        String name) {
}
