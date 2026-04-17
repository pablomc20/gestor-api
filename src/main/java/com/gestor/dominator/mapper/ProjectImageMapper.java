package com.gestor.dominator.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.gestor.dominator.dto.projectimage.ProjectImagesRecord;
import com.gestor.dominator.dto.projectimage.ProjectImagesResult;
import com.gestor.dominator.model.postgre.projectimage.ProjectImageRq;
import com.gestor.dominator.model.postgre.projectimage.ProjectImagesRs;

@Mapper(componentModel = "spring")
public interface ProjectImageMapper {
  @Mapping(target = "project_id", source = "record.projectId")
  @Mapping(target = "type", source = "record.type")
  ProjectImageRq toProjectImageRq(ProjectImagesRecord record);

  @Mapping(target = "imageId", source = "rs.image_id")
  @Mapping(target = "filename", source = "rs.filename")
  @Mapping(target = "ext", source = "rs.ext")
  @Mapping(target = "mymetype", source = "rs.mymetype")
  @Mapping(target = "visibility", source = "rs.visibility")
  ProjectImagesResult toProjectImagesResult(ProjectImagesRs rs);
}
