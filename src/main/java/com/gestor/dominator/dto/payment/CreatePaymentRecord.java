package com.gestor.dominator.dto.payment;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CreatePaymentRecord(
        String idContract,
        String type,
        BigDecimal amount,
        LocalDate paymentDate) {

}
