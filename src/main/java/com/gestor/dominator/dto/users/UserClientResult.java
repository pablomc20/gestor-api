package com.gestor.dominator.dto.users;

import lombok.Builder;

@Builder
public record UserClientResult(
        String id,
        String name) {

}
