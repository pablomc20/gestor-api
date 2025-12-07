package com.gestor.dominator.model.postgre.contract;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.Builder;

@Builder
public record CreateContractRecord(
    BigDecimal final_amount,
    UUID project_id,
    Integer number_payment) {

}
