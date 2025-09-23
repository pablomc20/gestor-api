package com.gestor.dominator.module;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;

@Builder
@Document("profiles")
public record Profile(
    @Id String id,
    String bio,
    String website
) {

}
