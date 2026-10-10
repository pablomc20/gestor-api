package com.gestor.dominator.business;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.gestor.dominator.constants.ImageType;
import com.gestor.dominator.constants.VisibilityType;
import com.gestor.dominator.dto.projectimage.CreateProjectImageRecord;
import com.gestor.dominator.dto.projectimage.ProjectImagesRecord;
import com.gestor.dominator.dto.projectimage.ProjectImagesResult;
import com.gestor.dominator.mapper.ProjectImageMapper;
import com.gestor.dominator.model.postgre.projectimage.CreateProjectImageRq;
import com.gestor.dominator.model.postgre.projectimage.CreateProjectImageRs;
import com.gestor.dominator.model.postgre.projectimage.ProjectImageRepository;
import com.gestor.dominator.model.postgre.projectimage.ProjectImageRq;
import com.gestor.dominator.service.projectimage.ProjectImageService;


@Service
public class ProjectImagesBusiness implements ProjectImageService {
  private final ProjectImageRepository projectImageRepository;
  private final ProjectImageMapper projectImageMapper;

  public ProjectImagesBusiness(ProjectImageRepository projectImageRepository, ProjectImageMapper projectImageMapper) {
    this.projectImageRepository = projectImageRepository;
    this.projectImageMapper = projectImageMapper;
  }

  @Override
  public List<ProjectImagesResult> getProjectImages(ProjectImagesRecord projectImagesRecord) {
    ProjectImageRq projectImageRq = projectImageMapper.toProjectImageRq(projectImagesRecord);
    return projectImageRepository.getProjectImages(projectImageRq).stream()
        .map(projectImageMapper::toProjectImagesResult)
        .toList();
  }

  @Override
  public UUID createProjectImage(CreateProjectImageRecord createProjectImageRecord) {
    CreateProjectImageRq projectImageRq = CreateProjectImageRq.builder()
        .projectId(createProjectImageRecord.projectId())
        .imageId(createProjectImageRecord.imageId())
        .type(ImageType.PORTAFOLIO.name())
        .visibility(VisibilityType.PUBLIC.name())
        .build();

    CreateProjectImageRs projectImageRs = projectImageRepository.save(projectImageRq);
    
    return projectImageRs.projectImageId();
  }

}
