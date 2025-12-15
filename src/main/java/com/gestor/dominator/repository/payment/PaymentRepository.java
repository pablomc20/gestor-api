package com.gestor.dominator.repository.payment;

import com.gestor.dominator.model.postgre.payment.PaymentCreateRq;
import com.gestor.dominator.model.postgre.payment.PaymentCreateRs;

public interface PaymentRepository {

    PaymentCreateRs createPayment(PaymentCreateRq paymentCreateRq);

}
