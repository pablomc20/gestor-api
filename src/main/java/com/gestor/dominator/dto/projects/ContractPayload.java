package com.gestor.dominator.dto.projects;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

@Builder
public record ContractPayload(
        UUID contractId,
        BigDecimal budget,
        @NotNull
        @Positive Integer numberPayment,
        Integer numberPaymentPaid,
        String description,
        String status,
        String fileUrl) {

}
