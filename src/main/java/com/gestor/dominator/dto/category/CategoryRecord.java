package com.gestor.dominator.dto.category;

import lombok.Builder;

@Builder
public record CategoryRecord(String name, String slug) {

}
