package com.gestor.dominator.dto.mobileappclick;

import lombok.Builder;

@Builder
public record MobileAppClickRecord(
        String eventType,
        String sessionId) {
}
