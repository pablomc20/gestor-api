package com.gestor.dominator.mapper;

import com.gestor.dominator.dto.projects.CreateProjectRecord;
import com.gestor.dominator.dto.projects.CreateProjectResult;
import com.gestor.dominator.dto.projects.DetailsForEmployeeRecord;
import com.gestor.dominator.dto.projects.DetailsForEmployeeResult;
import com.gestor.dominator.dto.projects.ProjectPayload;
import com.gestor.dominator.dto.projects.ProjectDetailsRecord;
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

    @Mapping(target = "budget", source = "contract.budget")
    CreateProjectRq toCreateRq(CreateProjectRecord createProject);

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

    @Mapping(target = "userIdClient", source = "rs.project.user_client")
    @Mapping(target = "userIdEmployee", source = "rs.project.user_employee")
    @Mapping(target = "title", source = "rs.project.title")
    @Mapping(target = "style", source = "rs.project.style")
    @Mapping(target = "size", source = "rs.project.size")
    @Mapping(target = "category", source = "rs.project.category")
    @Mapping(target = "chapes", source = "rs.project.chapes")
    @Mapping(target = "colors", source = "rs.project.colors")
    @Mapping(target = "materials", source = "rs.project.materials")
    @Mapping(target = "additionals", source = "rs.project.additionals")
    @Mapping(target = "startDate", source = "rs.project.start_date")
    @Mapping(target = "endDate", source = "rs.project.end_date")
    @Mapping(target = "projectId", source = "projectId")
    @Mapping(target = "status", source = "rs.project.status")
    ProjectPayload toDetailsProjectRs(ProjectDetailsRs rs, String projectId);
}
