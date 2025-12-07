package com.gestor.dominator.model.postgre.projects;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import lombok.Builder;

@Builder
public record CreateProjectRecord(
    String title,
    String description,
    String size,
    LocalDate startDate,
    LocalDate estimatedCompletionDate,
    BigDecimal budget,
    UUID category,
    UUID client,
    UUID employee,
    UUID[] colors,
    UUID[] materials,
    UUID[] images) {

}
