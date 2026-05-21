package com.gestor.dominator.business.project;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gestor.dominator.constants.StatusProject;
import com.gestor.dominator.dto.projects.usecase.ChangeStatusProjectRecord;
import com.gestor.dominator.exceptions.custom.DataValidationException;
import com.gestor.dominator.mapper.ProjectMapper;
import com.gestor.dominator.model.postgre.projectimage.ProjectImageRepository;
import com.gestor.dominator.model.postgre.projectstatus.CreateProjectStatusRq;
import com.gestor.dominator.repository.notification.NotifiactionRepository;
import com.gestor.dominator.repository.project.ProjectRepository;
import com.gestor.dominator.repository.projectstatus.ProjectStatusRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChangeStatusProjectUseCase {

    private final String VISIBILITY = "PRIVATE"; // For ProjectImage
    private final String NEW_STATUS = "NEW_STATUS"; // For Notification
    private final ProjectImageRepository projectImageRepository;
    private final ProjectRepository projectRepository;
    private final ProjectStatusRepository projectStatusRepository;
    private final NotifiactionRepository notificationRepository;
    private final ProjectMapper projectMapper;

    @Transactional
    public void execute(ChangeStatusProjectRecord record) {
        // Retrieve current status

        UUID idProject = UUID.fromString(record.projectId());
        String currentStatusValue = projectRepository.getStatusById(idProject);
        StatusProject currentStatus = StatusProject.fromValue(currentStatusValue);

        // Relacionar imagenes
        if (!currentStatus.imageType.isEmpty()) {
            if (record.imagesIds() == null || record.imagesIds().length == 0) {
                throw new DataValidationException("Images IDs are required for status: " + currentStatus.value);
            }
            saveProjectImages(record.imagesIds(), record.projectId(), currentStatus.imageType);
        }

        // Update to next status
        projectRepository.updateStatus(idProject, currentStatus.nextStatus);
        createProjectStatus(record, currentStatus.nextStatus);

        // Send notification if status was updated
        String message = currentStatus.statusMessage;
        sendNotification(message, record.projectId(), record.userId());
    }

    private void createProjectStatus(ChangeStatusProjectRecord record, String currentStatus) {
        CreateProjectStatusRq request = projectMapper.createProjectStatusRq(record, currentStatus);
        projectStatusRepository.createProjectStatus(request);
    }

    private void saveProjectImages(String[] idsImages, String idProject, String typeImage) {
        for (String idImage : idsImages) {
            projectImageRepository.save(projectMapper.projectImagesToRq(idImage, idProject, typeImage, VISIBILITY));
        }
    }

    private void sendNotification(String message, String projectId, String userId) {
        notificationRepository.create(projectMapper.notificationRq(message, projectId, userId, NEW_STATUS));
    }

}
