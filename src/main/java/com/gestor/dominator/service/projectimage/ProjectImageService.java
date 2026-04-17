package com.gestor.dominator.service.projectimage;

import java.util.List;

import com.gestor.dominator.dto.projectimage.ProjectImagesRecord;
import com.gestor.dominator.dto.projectimage.ProjectImagesResult;

public interface ProjectImageService {
  List<ProjectImagesResult> getProjectImages(ProjectImagesRecord projectImagesRecord);
}
