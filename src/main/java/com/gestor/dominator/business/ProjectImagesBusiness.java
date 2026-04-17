package com.gestor.dominator.business;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gestor.dominator.dto.projectimage.ProjectImagesRecord;
import com.gestor.dominator.dto.projectimage.ProjectImagesResult;
import com.gestor.dominator.mapper.ProjectImageMapper;
import com.gestor.dominator.model.postgre.projectimage.ProjectImageRepository;
import com.gestor.dominator.model.postgre.projectimage.ProjectImageRq;
import com.gestor.dominator.service.projectimage.ProjectImageService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectImagesBusiness implements ProjectImageService {
  private final ProjectImageRepository projectImageRepository;
  private final ProjectImageMapper projectImageMapper;

  @Override
  public List<ProjectImagesResult> getProjectImages(ProjectImagesRecord projectImagesRecord) {
    ProjectImageRq projectImageRq = projectImageMapper.toProjectImageRq(projectImagesRecord);
    return projectImageRepository.getProjectImages(projectImageRq).stream()
        .map(projectImageMapper::toProjectImagesResult)
        .toList();
  }

}
