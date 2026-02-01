package com.gestor.dominator.model.client.dipomex;

import java.util.List;

import lombok.Builder;

@Builder
public record EstadoResponse(
        boolean error,
        String message,
        List<Estado> estados) {

}
