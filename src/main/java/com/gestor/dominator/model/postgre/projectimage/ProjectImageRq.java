package com.gestor.dominator.model.postgre.projectimage;

import java.util.UUID;

import com.gestor.dominator.constants.ImageType;

public record ProjectImageRq(
  UUID project_id,
  ImageType type
) {
  
}
