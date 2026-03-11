package com.gestor.dominator.dto.projects.usecase;

public record ChangeStatusProjectRecord(
  String projectId, String userId, String[] imagesIds
) {
  
}
