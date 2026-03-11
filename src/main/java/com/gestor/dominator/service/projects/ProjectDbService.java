package com.gestor.dominator.service.projects;

import com.gestor.dominator.model.postgre.projectimage.CreateProjectImageRq;
import com.gestor.dominator.model.postgre.projectimage.CreateProjectImageRs;

public interface ProjectDbService {
    CreateProjectImageRs save(CreateProjectImageRq rq);
}
