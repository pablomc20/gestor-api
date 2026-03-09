package com.gestor.dominator.service.projects;

import java.util.List;
import java.util.UUID;

import com.gestor.dominator.dto.payment.CreatePaymentRecord;
import com.gestor.dominator.dto.payment.CreatePaymentResult;
import com.gestor.dominator.dto.payment.ReadPaymentResult;

public interface PaymentService {
    CreatePaymentResult createPayment(CreatePaymentRecord createPaymentRecord);

    List<ReadPaymentResult> getPaymentsByContractId(UUID contractId);
}
