package com.gestor.dominator.model.dipomex;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;

@Builder
public record Estado(
        @JsonProperty("ESTADO_ID") String id,
        @JsonProperty("ESTADO") String nombre) {

}
