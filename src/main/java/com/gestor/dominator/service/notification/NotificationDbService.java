package com.gestor.dominator.service.notification;

import com.gestor.dominator.model.postgre.notification.NotificationSendRq;

public interface NotificationDbService {
    void create(NotificationSendRq notificationSendRq);
    
}
