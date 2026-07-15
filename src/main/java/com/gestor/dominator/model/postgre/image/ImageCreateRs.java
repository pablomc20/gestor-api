package com.gestor.dominator.model.postgre.image;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;

@Builder
public record ImageCreateRs(
        String status,
        @JsonProperty("image_id") UUID idImage) {

}
