package com.gestor.dominator.model.postgre.category;

import lombok.Builder;

@Builder
public record CategoryRq(String name, String slug) {

}
