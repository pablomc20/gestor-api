package com.gestor.dominator.model.postgre.payment;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PaymentCreateRq(
      String contract_id,
      String type,
      BigDecimal amount,
      LocalDate payment_date) {

}
