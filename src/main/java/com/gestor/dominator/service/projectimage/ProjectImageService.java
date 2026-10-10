package com.gestor.dominator.service.projectimage;

import java.util.List;
import java.util.UUID;

import com.gestor.dominator.dto.projectimage.CreateProjectImageRecord;
import com.gestor.dominator.dto.projectimage.ProjectImagesRecord;
import com.gestor.dominator.dto.projectimage.ProjectImagesResult;

public interface ProjectImageService {
  List<ProjectImagesResult> getProjectImages(ProjectImagesRecord projectImagesRecord);
  UUID createProjectImage(CreateProjectImageRecord createProjectImageRecord);
}
