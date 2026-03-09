package com.gestor.dominator.model.postgre.notification;

import java.util.UUID;

import lombok.Builder;

@Builder
public record NotificationSendRq(
  String type, String message, UUID projectId, UUID userId
) {

}
