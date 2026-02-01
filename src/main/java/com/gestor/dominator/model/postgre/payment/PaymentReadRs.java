package com.gestor.dominator.model.postgre.payment;

import java.time.LocalDate;

public record PaymentReadRs(
        String payment_id,
        double amount,
        LocalDate payment_date,
        String type,
        boolean paid) {

}
