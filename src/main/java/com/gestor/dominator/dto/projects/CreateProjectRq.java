package com.gestor.dominator.dto.projects;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import jakarta.validation.Valid;
import lombok.Builder;

@Builder
public record CreateProjectRq(
                String title,
                String description,
                String size,
                LocalDate startDate,
                LocalDate estimatedCompletionDate,
                BigDecimal budget,
                UUID category,
                @Valid UUID client,
                UUID employee,
                UUID[] colors,
                UUID[] materials,
                UUID[] images) {

}
