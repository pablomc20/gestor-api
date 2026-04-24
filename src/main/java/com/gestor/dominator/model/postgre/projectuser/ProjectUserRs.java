package com.gestor.dominator.model.postgre.projectuser;

import java.time.LocalDate;
import lombok.Builder;

@Builder
public record ProjectUserRs(
        String projectId,
        String title,
        LocalDate startDate,
        LocalDate estimatedCompletionDate,
        Integer daysRemaining,
        String clientName,
        String status,
        String userId) {
}
