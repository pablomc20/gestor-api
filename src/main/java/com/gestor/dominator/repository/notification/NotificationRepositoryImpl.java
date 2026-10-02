package com.gestor.dominator.repository.notification;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.gestor.dominator.model.postgre.notification.NotificationSendRq;


@Repository
public class NotificationRepositoryImpl implements NotifiactionRepository {

    private final JdbcTemplate jdbcTemplate;

    public NotificationRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean create(NotificationSendRq notificationSendRq) {
        return jdbcTemplate.update(
                NotificationQueryBD.GET_NOTIFICATIONS_BY_USER_ID,
                mapCreateNotificationParams(notificationSendRq)) > 0;
    }

    private Object[] mapCreateNotificationParams(NotificationSendRq r) {
        return new Object[] {
                r.type(),
                r.message(),
                r.projectId(),
                r.userId()
        };
    }

}
