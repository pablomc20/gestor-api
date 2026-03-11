package com.gestor.dominator.business.project;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gestor.dominator.constants.StatusProject;
import com.gestor.dominator.dto.projects.usecase.ChangeStatusProjectRecord;
import com.gestor.dominator.exceptions.custom.PostgreDbException;
import com.gestor.dominator.model.postgre.notification.NotificationSendRq;
import com.gestor.dominator.model.postgre.projectimage.CreateProjectImageRq;
import com.gestor.dominator.service.notification.NotificationDbService;
import com.gestor.dominator.service.projects.ProjectDbService;
import com.gestor.dominator.service.projects.ProjectImageDbService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChangeStatusProjectUseCase {

  private final String VISIBILITY = "PRIVATE";
  private final ProjectImageDbService projectImageDbService;
  private final ProjectDbService projectDbService;
  private final NotificationDbService notificationDbService;

  @Transactional
  public void execute(ChangeStatusProjectRecord record) {
    // Retrieve current status
    String projectId = record.projectId();
    String currentStatus = retierveCurrentStatus(record.projectId());

    // Relacionar imagenes
    String typeImage = getTypeImage(currentStatus);
    if (!typeImage.isEmpty()) {
      createRelation(record.imagesIds(), record.projectId());
    }

    // Update to next status
    String nextStatus = StatusProject.retrieveNextStatusProject(currentStatus);
    projectDbService.updateStatus(projectId, nextStatus);

    // Send notification if status was updated
    String userId = record.userId();
    String message = StatusProject.getStatusMessage(currentStatus);
    sendNotification(message, projectId, userId);
  }

  private String retierveCurrentStatus(String projectId) {
    String currentStatus = projectDbService.getStatusById(projectId);
    if (currentStatus == null) {
      throw new PostgreDbException("Proyecto no encontrado");
    }
    return currentStatus;
  }

  public void createRelation(String[] idsImages, String idProject) {

    for (String idImage : idsImages) {
      CreateProjectImageRq createProjectImageRq = CreateProjectImageRq.builder()
          .projectId(UUID.fromString(idProject))
          .imageId(UUID.fromString(idImage))
          .type(VISIBILITY)
          .visibility(VISIBILITY)
          .build();
      projectImageDbService.save(createProjectImageRq);
    }
  }

  private void sendNotification(String message, String projectId, String userId) {
    NotificationSendRq notification = NotificationSendRq.builder()
        .type("NEW_STATUS")
        .message(message)
        .projectId(UUID.fromString(projectId))
        .userId(UUID.fromString(userId))
        .build();
    notificationDbService.create(notification);
  }

  private String getTypeImage(String statusProject) {
    return StatusProject.ASSEMBLED.value == statusProject
        ? "PROGRESS"
        : StatusProject.FINISHED.value == statusProject 
          ? "FINAL" : "";
  }

}
