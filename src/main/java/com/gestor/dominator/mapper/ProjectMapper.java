package com.gestor.dominator.mapper;

import com.gestor.dominator.dto.projects.CreateProjectRecord;
import com.gestor.dominator.dto.projects.CreateProjectResult;
import com.gestor.dominator.dto.projects.ProjectPayload;
import com.gestor.dominator.dto.projects.StatusProjectResult;
import com.gestor.dominator.dto.projectuser.ProjectUserResult;
import com.gestor.dominator.dto.projects.usecase.DetailsByIdRecord;
import com.gestor.dominator.dto.projects.usecase.DetailsByIdResult;
import com.gestor.dominator.dto.projects.ProjectDetailsRecord;
import com.gestor.dominator.model.postgre.notification.NotificationSendRq;
import com.gestor.dominator.model.postgre.project.CreateProjectRq;
import com.gestor.dominator.model.postgre.project.CreateProjectRs;
import com.gestor.dominator.model.postgre.project.DetailsByIdRs;
import com.gestor.dominator.model.postgre.project.DetailsByIdRq;
import com.gestor.dominator.model.postgre.project.ProjectDetailsRq;
import com.gestor.dominator.model.postgre.project.ProjectDetailsRs;
import com.gestor.dominator.model.postgre.projectuser.ProjectUserRs;
import com.gestor.dominator.model.postgre.projectimage.CreateProjectImageRq;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProjectMapper {

    @Mapping(target = "budget", source = "contract.budget")
    CreateProjectRq toCreateRq(CreateProjectRecord createProject);

    @Mapping(target = "idProject", source = "project_id")
    CreateProjectResult toResult(CreateProjectRs result);

    DetailsByIdRq toDetailsRq(DetailsByIdRecord detailsForEmployeeRecord);

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
    DetailsByIdResult toDetailsRs(DetailsByIdRs detailsForClientRs);

    List<DetailsByIdResult> toDetailsRs(List<DetailsByIdRs> detailsForEmployeeRs);

    ProjectUserResult toProjectUserResult(ProjectUserRs projectUserRs);

    List<ProjectUserResult> toProjectUserResult(List<ProjectUserRs> projectUserRs);

    ProjectDetailsRq toDetailsProjectRq(ProjectDetailsRecord projectDetailsRecord);

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
    @Mapping(target = "realEndDate", source = "rs.project.real_end_date")
    @Mapping(target = "projectId", source = "projectId")
    @Mapping(target = "status", source = "rs.project.status")
    ProjectPayload toDetailsProjectRs(ProjectDetailsRs rs, String projectId);

    @Mapping(target = "currentStatus", source = "currentStatus")
    @Mapping(target = "nextStatus", source = "nextStatus")
    StatusProjectResult toStatusProjectResult(String currentStatus, String nextStatus);

    @Mapping(target = "projectId", source = "idProject")
    @Mapping(target = "imageId", source = "idImage")
    @Mapping(target = "type", source = "typeImage")
    @Mapping(target = "visibility", source = "visibility")
    CreateProjectImageRq projectImagesToRq(String idImage, String idProject, String typeImage, String visibility);

    @Mapping(target = "projectId", source = "projectId")
    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "type", source = "typeImage")
    @Mapping(target = "message", source = "message")
    NotificationSendRq notificationRq(String message, String projectId, String userId, String typeImage);

}
