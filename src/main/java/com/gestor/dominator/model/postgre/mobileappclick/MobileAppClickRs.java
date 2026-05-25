package com.gestor.dominator.model.postgre.mobileappclick;

import java.time.OffsetDateTime;
import lombok.Builder;

@Builder
public record MobileAppClickRs(
        Integer id,
        String eventType,
        OffsetDateTime eventDate,
        String sessionId) {
}
