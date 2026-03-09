package com.gestor.dominator.repository.notification;

public final class NotificationQueryBD {
      private NotificationQueryBD() {
      }
  
      public static final String GET_NOTIFICATIONS_BY_USER_ID = """
              INSERT INTO notifications (type, message, project_id, user_id) VALUES
              (?::notification_type, ?, ?, ?);
          """;

}
