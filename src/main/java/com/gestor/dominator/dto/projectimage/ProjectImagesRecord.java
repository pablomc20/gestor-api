package com.gestor.dominator.dto.projectimage;

import com.gestor.dominator.constants.ImageType;

import lombok.Builder;

@Builder
public record ProjectImagesRecord(
  String projectId,
  ImageType type
) {
  
}
