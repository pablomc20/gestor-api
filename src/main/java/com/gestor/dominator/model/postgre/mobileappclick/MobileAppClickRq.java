package com.gestor.dominator.model.postgre.mobileappclick;

import lombok.Builder;

@Builder
public record MobileAppClickRq(
        String eventType,
        String sessionId) {
}
