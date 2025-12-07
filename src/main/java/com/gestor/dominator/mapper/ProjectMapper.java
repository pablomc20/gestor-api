package com.gestor.dominator.mapper;

import com.gestor.dominator.dto.projects.CreateProjectRq;
import com.gestor.dominator.dto.projects.CreateProjectRs;
import com.gestor.dominator.model.postgre.project.CreateProjectRecord;
import com.gestor.dominator.model.postgre.project.CreateProjectResult;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProjectMapper {
  CreateProjectRecord toRecord(CreateProjectRq rq);

  @Mapping(target = "idProject", source = "project_id")
  CreateProjectRs toRs(CreateProjectResult result);
}
