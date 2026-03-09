package com.gestor.dominator.dto.payment;

public record ReadPaymentResult(
        String paymentId,
        String type,
        boolean paid,
        double amount,
        String paymentDate) {

}
