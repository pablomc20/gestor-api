package com.gestor.dominator.dto.projectimage;

import lombok.Builder;

@Builder
public record ProjectImagesResult(
  String imageId,
  String filename,
  String ext,
  String mymetype,
  String visibility
) {
  
}
