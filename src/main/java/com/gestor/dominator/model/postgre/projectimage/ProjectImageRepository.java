package com.gestor.dominator.model.postgre.projectimage;

import java.util.List;

public interface ProjectImageRepository {

    CreateProjectImageRs save(CreateProjectImageRq rq);

    List<ProjectImagesRs> getProjectImages(ProjectImageRq rq);
}
