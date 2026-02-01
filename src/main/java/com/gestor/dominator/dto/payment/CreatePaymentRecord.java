package com.gestor.dominator.dto.payment;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record CreatePaymentRecord(
        UUID idContract,
        String type,
        BigDecimal amount,
        LocalDate paymentDate) {

}
