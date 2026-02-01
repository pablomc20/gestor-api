package com.gestor.dominator.model.postgre.contract;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Builder;

@Builder
public record ReadContractRs(
        String contract_id,
        String status,
        String file_url,
        LocalDate signed_at,
        BigDecimal final_amount,
        Integer number_payments,
        String description) {

}
