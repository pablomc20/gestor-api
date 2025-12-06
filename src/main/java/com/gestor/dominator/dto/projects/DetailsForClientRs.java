package com.gestor.dominator.dto.projects;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Builder
public record DetailsForClientRs(
        String title,
        LocalDate startDate,
        LocalDate estimatedCompletionDate,
        BigDecimal finalAmount,
        UUID projectId,
        String status, // project_status enum - cambiar a enum cuando esté definido
        UUID requestId,
        String type, // request_type enum - cambiar a enum cuando esté definido
        Integer numberPayments,
        Integer currentPayment,
        String phoneEmployee) {
}
