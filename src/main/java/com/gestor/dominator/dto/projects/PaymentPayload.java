package com.gestor.dominator.dto.projects;

import java.time.LocalDate;

import com.gestor.dominator.constants.PaymentType;

import lombok.Builder;

@Builder
public record PaymentPayload(
        Double amount,
        PaymentType type,
        LocalDate paymentDate,
        boolean isPaid,
        String paymentId) {

}
