package com.gestor.dominator.dto.projects;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import lombok.Builder;

@Builder
public record CreateProjectRecord(
        String title,
        String size,
        String style,
        String additionalInfo,
        LocalDate startDate,
        LocalDate estimatedCompletionDate,
        BigDecimal budget,
        UUID category,
        UUID client,
        UUID employee,

        Integer number_payment,

        UUID[] colors,
        UUID[] materials,
        UUID[] images,
        UUID[] chapes) {

}
