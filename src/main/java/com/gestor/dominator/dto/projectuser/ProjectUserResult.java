package com.gestor.dominator.dto.projectuser;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Builder;

@Builder
public record ProjectUserResult(
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
