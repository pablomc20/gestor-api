package com.gestor.dominator.module;

import java.time.LocalDateTime;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;

@Builder
@Document(collection = "users_test")
public record Author(
    @Id String id,
    String username,
    String password,
    List<String> roles,
    LocalDateTime createdAt,

    ObjectId profileId
) {
    
}
