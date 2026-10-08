package com.gestor.dominator.dto.projects;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.gestor.dominator.constants.PaymentType;

import lombok.Builder;

@Builder
public record PaymentPayload(
        BigDecimal amount,
        PaymentType type,
        LocalDate paymentDate,
        boolean isPaid,
        String paymentId) {

}
