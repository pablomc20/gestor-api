package com.gestor.dominator.model.postgre.payment;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Builder;

@Builder
public record PaymentCreateRq(
        String contract_id,
        String type,
        BigDecimal amount,
        LocalDate payment_date) {

}
