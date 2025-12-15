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
        UUID category,
        UUID client,
        UUID employee,

        BigDecimal budget,
        Integer number_payment,
        String description,

        UUID[] colors,
        UUID[] materials,
        UUID[] images,
        UUID[] chapes) {

}
