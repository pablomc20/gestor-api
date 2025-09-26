package com.gestor.dominator.dto.tag;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record TagResponse(
    String id,
    String name,
    String slug,
    String type,
    String group
) {
}