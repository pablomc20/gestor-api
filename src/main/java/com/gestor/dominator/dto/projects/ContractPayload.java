package com.gestor.dominator.dto.projects;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.Builder;

@Builder
public record ContractPayload(
        UUID contractId,
        BigDecimal budget,
        Integer numberPayment,
        Integer numberPaymentPaid,
        String description,
        String status,
        String fileUrl) {

}
