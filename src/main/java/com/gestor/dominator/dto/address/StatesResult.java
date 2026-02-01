package com.gestor.dominator.dto.address;

import java.util.List;

import lombok.Builder;

@Builder
public record StatesResult(
    List<String> estados) {
}
