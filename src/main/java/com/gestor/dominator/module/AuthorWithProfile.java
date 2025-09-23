package com.gestor.dominator.module;

import java.util.List;

import lombok.Builder;

@Builder
public record AuthorWithProfile(
    String id,
    String username,
    List<String> roles,
    Profile profile
) {
    
}
