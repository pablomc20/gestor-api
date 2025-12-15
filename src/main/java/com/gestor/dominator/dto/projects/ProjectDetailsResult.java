package com.gestor.dominator.dto.projects;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ProjectDetailsResult(
        String title,
        LocalDate startDate,
        LocalDate estimatedCompletionDate,
        BigDecimal finalAmount,
        String status,
        String requestId,
        String type,
        Integer numberPayments,
        Integer currentPayment,
        String colors,
        String materials) {

}
