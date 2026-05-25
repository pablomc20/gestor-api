package com.gestor.dominator.dto.mobileappclick;

import java.time.OffsetDateTime;
import lombok.Builder;

@Builder
public record MobileAppClickResult(
        Integer id,
        String eventType,
        OffsetDateTime eventDate,
        String sessionId) {
}
