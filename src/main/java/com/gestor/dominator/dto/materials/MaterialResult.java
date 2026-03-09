package com.gestor.dominator.dto.materials;

import java.util.List;

import lombok.Builder;

@Builder
public record MaterialResult(List<MaterialPayload> materials) {

}
