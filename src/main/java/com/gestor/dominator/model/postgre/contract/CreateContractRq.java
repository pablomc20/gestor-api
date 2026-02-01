package com.gestor.dominator.model.postgre.contract;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.Builder;

@Builder
public record CreateContractRq(
        BigDecimal finalAmount,
        UUID projectId,
        Integer numberPayment,
        String description) {

}
