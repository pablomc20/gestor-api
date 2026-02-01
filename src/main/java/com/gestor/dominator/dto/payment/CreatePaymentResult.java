package com.gestor.dominator.dto.payment;

import lombok.Builder;

@Builder
public record CreatePaymentResult(
        String idPayment) {

}
