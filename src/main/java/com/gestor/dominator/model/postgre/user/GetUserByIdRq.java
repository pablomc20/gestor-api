package com.gestor.dominator.model.postgre.user;

import java.util.UUID;

import lombok.Builder;

@Builder
public record GetUserByIdRq(UUID userId) {

}
