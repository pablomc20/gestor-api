package com.gestor.dominator.model.postgre.image;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ImageCreateRs(
        String status,
        @JsonProperty("image_id") String idImage) {

}
