package com.gestor.dominator.model.postgre.material;

import lombok.Builder;

@Builder
public record MaterialRq(String name, String slug) {
}
