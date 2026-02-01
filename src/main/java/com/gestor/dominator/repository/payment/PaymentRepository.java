package com.gestor.dominator.repository.payment;

import java.util.List;
import java.util.UUID;

import com.gestor.dominator.model.postgre.payment.PaymentCreateRq;
import com.gestor.dominator.model.postgre.payment.PaymentCreateRs;
import com.gestor.dominator.model.postgre.payment.PaymentReadRs;

public interface PaymentRepository {

    PaymentCreateRs createPayment(PaymentCreateRq paymentCreateRq);

    List<PaymentReadRs> getPayments(UUID contractId);

    void validateFirstPaymentExists(UUID contractId);
}
