package com.gestor.dominator.dto.projects;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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

        @Valid
        @NotNull ContractPayload contract,
        PaymentPayload[] payments,

        UUID[] colors,
        UUID[] materials,
        UUID[] images,
        UUID[] chapes) {

}
