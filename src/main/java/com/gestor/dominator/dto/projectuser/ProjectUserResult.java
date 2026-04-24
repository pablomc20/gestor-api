package com.gestor.dominator.dto.projectuser;

import java.time.LocalDate;
import lombok.Builder;

@Builder
public record ProjectUserResult(
        String projectId,
        String title,
        LocalDate startDate,
        LocalDate estimatedCompletionDate,
        Integer daysRemaining,
        String clientName,
        String status,
        String userId) {
}
