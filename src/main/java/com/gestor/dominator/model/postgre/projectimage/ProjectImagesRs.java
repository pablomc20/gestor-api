package com.gestor.dominator.model.postgre.projectimage;

import java.util.UUID;

public record ProjectImagesRs(
  UUID image_id,
  String filename,
  String ext,
  String mymetype,
  String visibility
) {

}
