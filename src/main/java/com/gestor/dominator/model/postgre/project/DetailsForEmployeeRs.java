package com.gestor.dominator.model.postgre.project;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import lombok.Builder;

@Builder
public record DetailsForEmployeeRs(
        String title,
        LocalDate startDate,
        LocalDate estimatedCompletionDate,
        BigDecimal finalAmount,
        Integer daysDifference,
        String name,
        UUID projectId,
        String status, // project_status enum - cambiar a enum cuando esté definido
        UUID requestId,
        UUID userId,
        String type, // request_type enum - cambiar a enum cuando esté definido
        Integer numberPayments,
        Integer currentPayment,
        String phoneEmployee) {

}
