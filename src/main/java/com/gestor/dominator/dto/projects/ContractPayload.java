package com.gestor.dominator.dto.projects;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.Builder;

@Builder
public record ContractPayload(
        UUID contractId,
        BigDecimal budget,
        Integer number_payment,
        Integer number_payment_paid,
        String description) {

}
