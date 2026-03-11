package com.gestor.dominator.service.notification;

import org.springframework.stereotype.Service;

import com.gestor.dominator.exceptions.custom.PostgreDbException;
import com.gestor.dominator.model.postgre.notification.NotificationSendRq;
import com.gestor.dominator.repository.notification.NotifiactionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationDbServiceImpl implements NotificationDbService {

    private final NotifiactionRepository notificationRepository;

    @Override
    public void create(NotificationSendRq notificationSendRq) {
        NotificationSendRq notification = NotificationSendRq.builder()
                .type(notificationSendRq.type())
                .message(notificationSendRq.message())
                .projectId(notificationSendRq.projectId())
                .userId(notificationSendRq.userId())
                .build();

        boolean isNotified = notificationRepository.createNotification(notification);
        if (!isNotified) {
            throw new PostgreDbException("Falló al enviar la notificación");
        }
    }

}
