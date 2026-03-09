package com.gestor.dominator.repository.notification;

import com.gestor.dominator.model.postgre.notification.NotificationSendRq;

public interface NotifiactionRepository {
  boolean createNotification(NotificationSendRq notificationSendRq);
}
