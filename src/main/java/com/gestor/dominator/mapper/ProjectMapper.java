package com.gestor.dominator.mapper;

import com.gestor.dominator.dto.projects.CreateProjectRecord;
import com.gestor.dominator.dto.projects.CreateProjectResult;
import com.gestor.dominator.dto.projects.DetailsForEmployeeRecord;
import com.gestor.dominator.dto.projects.DetailsForEmployeeResult;
import com.gestor.dominator.dto.projects.ProjectDetailsRecord;
import com.gestor.dominator.dto.projects.ProjectDetailsResult;
import com.gestor.dominator.model.postgre.project.CreateProjectRq;
import com.gestor.dominator.model.postgre.project.CreateProjectRs;
import com.gestor.dominator.model.postgre.project.DetailsForClientRs;
import com.gestor.dominator.model.postgre.project.DetailsForEmployeeRq;
import com.gestor.dominator.model.postgre.project.ProjectDetailsRq;
import com.gestor.dominator.model.postgre.project.ProjectDetailsRs;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProjectMapper {
  CreateProjectRq toRq(CreateProjectRecord createProject);

  @Mapping(target = "idProject", source = "project_id")
  CreateProjectResult toResult(CreateProjectRs result);

  DetailsForEmployeeRq toDetailsRq(DetailsForEmployeeRecord detailsForEmployeeRecord);

  @Mapping(target = "startDate", source = "started")
  @Mapping(target = "estimatedCompletionDate", source = "estimated")
  @Mapping(target = "daysDifference", source = "days_difference")
  @Mapping(target = "projectId", source = "project_id")
  @Mapping(target = "userId", source = "user_id")
  @Mapping(target = "type", source = "type_desc")
  @Mapping(target = "finalAmount", ignore = true)
  @Mapping(target = "numberPayments", ignore = true)
  @Mapping(target = "currentPayment", ignore = true)
  @Mapping(target = "phoneEmployee", ignore = true)
  @Mapping(target = "requestId", ignore = true)
  DetailsForEmployeeResult toDetailsRs(DetailsForClientRs detailsForClientRs);

  List<DetailsForEmployeeResult> toDetailsRs(List<DetailsForClientRs> detailsForEmployeeRs);

  ProjectDetailsRq toDetailsProjectRq(ProjectDetailsRecord projectDetailsRecord);

  @Mapping(target = "startDate", source = "started")
  @Mapping(target = "estimatedCompletionDate", source = "estimated")
  @Mapping(target = "finalAmount", source = "final_amount")
  @Mapping(target = "status", source = "status")
  @Mapping(target = "requestId", source = "request_id")
  @Mapping(target = "type", source = "type")
  @Mapping(target = "numberPayments", source = "number_payments")
  @Mapping(target = "currentPayment", source = "payments_count")
  ProjectDetailsResult toDetailsProjectRs(ProjectDetailsRs projectDetailsRs);
}
