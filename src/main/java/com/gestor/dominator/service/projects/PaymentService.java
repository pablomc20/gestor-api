package com.gestor.dominator.service.projects;

import com.gestor.dominator.dto.payment.CreatePaymentRecord;
import com.gestor.dominator.dto.payment.CreatePaymentResult;

public interface PaymentService {
    CreatePaymentResult createPayment(CreatePaymentRecord createPaymentRecord);
}
