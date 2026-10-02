package com.gestor.dominator.model.postgre.projectuser;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Builder;

@Builder
public record ProjectUserRs(
        String projectId,
        String title,
        LocalDate startDate,
        LocalDate estimatedCompletionDate,
        LocalDate actualCompletionDate,
        LocalDateTime lastStatusDate,
        Integer daysRemaining,
        String clientName,
        String phone,
        String status,
        String userId) {
}
