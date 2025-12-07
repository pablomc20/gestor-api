package com.gestor.dominator.mapper;

import com.gestor.dominator.dto.projects.CreateProjectRecord;
import com.gestor.dominator.dto.projects.CreateProjectResult;
import com.gestor.dominator.dto.projects.DetailsForEmployeeRecord;
import com.gestor.dominator.dto.projects.DetailsForEmployeeResult;
import com.gestor.dominator.model.postgre.project.CreateProjectRq;
import com.gestor.dominator.model.postgre.project.CreateProjectRs;
import com.gestor.dominator.model.postgre.project.DetailsForEmployeeRq;
import com.gestor.dominator.model.postgre.project.DetailsForEmployeeRs;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProjectMapper {
  CreateProjectRq toRq(CreateProjectRecord createProject);

  @Mapping(target = "idProject", source = "project_id")
  CreateProjectResult toResult(CreateProjectRs result);

  DetailsForEmployeeRq toDetailsRq(DetailsForEmployeeRecord detailsForEmployeeRecord);

  List<DetailsForEmployeeResult> toDetailsRs(List<DetailsForEmployeeRs> detailsForEmployeeRs);
}
