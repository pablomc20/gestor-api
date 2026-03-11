package com.gestor.dominator.business.project;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gestor.dominator.constants.StatusProject;
import com.gestor.dominator.dto.projects.usecase.ChangeStatusProjectRecord;
import com.gestor.dominator.dto.projects.usecase.UploadImageRecord;
import com.gestor.dominator.exceptions.custom.PostgreDbException;
import com.gestor.dominator.model.postgre.notification.NotificationSendRq;
import com.gestor.dominator.model.postgre.projectimage.CreateProjectImageRq;
import com.gestor.dominator.repository.notification.NotifiactionRepository;
import com.gestor.dominator.repository.project.ProjectRepository;
import com.gestor.dominator.service.projects.ProjectDbService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChangeStatusProjectUseCase {

  private final ProjectDbService projectDbService;
  private final ProjectRepository projectRepository;
  private final NotifiactionRepository notificationRepository;

  @Transactional
  public void execute(ChangeStatusProjectRecord record) {
    // Retrieve current status
    UUID projectId = UUID.fromString(record.projectId());
    String currentStatus = retierveCurrentStatus(projectId);

    // Relacionar imagenes
    createRelation(record.imagesIds(), record.projectId());

    // Update to next status
    String nextStatus = StatusProject.retrieveNextStatusProject(currentStatus);
    updateStatusProject(projectId, nextStatus);
    
    // Send notification if status was updated
    UUID userId = UUID.fromString(record.userId());
    String message = StatusProject.getStatusMessage(currentStatus);
    sendNotification(message, projectId, userId);
  }

  private String retierveCurrentStatus(UUID projectId) {
    String currentStatus = projectRepository.getStatusById(projectId);
    if (currentStatus == null) {
      throw new PostgreDbException("Proyecto no encontrado");
    }
    return currentStatus;
  }

  public void createRelation(String[] idsImages, String idProject) {

    String type = "INITIAL";
    String visibility = "PRIVATE";
    
    for (String idImage : idsImages) {
      CreateProjectImageRq createProjectImageRq = CreateProjectImageRq.builder()
              .projectId(UUID.fromString(idProject))
              .imageId(UUID.fromString(idImage))
              .type(type)
              .visibility(visibility)
              .build();
      projectDbService.save(createProjectImageRq);
    }
}

  private void updateStatusProject(UUID projectId, String nextStatus) {
    boolean isUpdated = projectRepository.updateStatusProject(projectId, nextStatus);
    if (!isUpdated) {
      throw new PostgreDbException("Falló al cambiar el estatus");
    }
  }

  private void sendNotification(String message, UUID projectId, UUID userId) {
    NotificationSendRq notification = NotificationSendRq.builder()
        .type("NEW_STATUS")
        .message(message)
        .projectId(projectId)
        .userId(userId)
        .build();

    boolean isNotified = notificationRepository.createNotification(notification);
    if (!isNotified) {
      throw new PostgreDbException("Falló al enviar la notificación");
    }
  }

}
