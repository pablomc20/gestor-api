package com.gestor.dominator.model.postgre.project;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Builder;

@Builder
public record ProjectDetailsRs(
        String title,
        LocalDate started,
        LocalDate estimated,
        BigDecimal final_amount,
        String status,
        String request_id,
        String type,
        Integer number_payments,
        Integer payments_count,
        String colors,
        String materials) {

}
