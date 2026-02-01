package com.gestor.dominator.service.projects;

public record ReadPaymentResult(
        String paymentId,
        String type,
        boolean paid,
        double amount,
        String paymentDate) {

}
