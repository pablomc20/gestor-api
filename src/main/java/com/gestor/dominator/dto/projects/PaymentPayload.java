package com.gestor.dominator.dto.projects;

import java.time.LocalDate;

import lombok.Builder;

@Builder
public record PaymentPayload(
        Double amount,
        String type,
        LocalDate paymentDate,
        boolean isPaid,
        String paymentId) {

}
