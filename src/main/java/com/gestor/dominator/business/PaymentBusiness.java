package com.gestor.dominator.business;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.gestor.dominator.constants.PaymentType;
import com.gestor.dominator.dto.payment.CreatePaymentRecord;
import com.gestor.dominator.dto.payment.CreatePaymentResult;
import com.gestor.dominator.dto.payment.ReadPaymentResult;
import com.gestor.dominator.exceptions.custom.DataValidationException;
import com.gestor.dominator.mapper.PaymentMapper;
import com.gestor.dominator.model.postgre.payment.PaymentCreateRq;
import com.gestor.dominator.model.postgre.payment.PaymentCreateRs;
import com.gestor.dominator.repository.payment.PaymentRepository;
import com.gestor.dominator.service.payment.PaymentService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentBusiness implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;

    @Override
    public CreatePaymentResult createPayment(CreatePaymentRecord createPaymentRecord) {

        validatePayload(createPaymentRecord);

        if (!PaymentType.FIRST.getValue().equals(createPaymentRecord.type())) {
            paymentRepository.validateFirstPaymentExists(createPaymentRecord.idContract());
        }

        PaymentCreateRq paymentCreateRecord = paymentMapper.toPaymentCreateRq(createPaymentRecord);

        // Crear pago en la BD
        PaymentCreateRs paymentCreateRs = paymentRepository.createPayment(paymentCreateRecord);

        return CreatePaymentResult.builder()
                .idPayment(paymentCreateRs.payment_id())
                .build();
    }

    @Override
    public List<ReadPaymentResult> getPaymentsByContractId(UUID contractId) {
        return paymentMapper.toPaymentReadedRs(
                paymentRepository.getPayments(contractId));
    }

    // ****** MÉTODOS AUXILIARES ******
    private void validatePayload(CreatePaymentRecord createPaymentRecord) {
        PaymentType.fromValue(createPaymentRecord.type());

        require(createPaymentRecord.idContract(), "Id contract is required");
        require(createPaymentRecord.type(), "Type is required");
        require(createPaymentRecord.amount(), "Amount is required");

    }

    private void require(Object value, String message) {
        if (value == null) {
            throw new DataValidationException(message);
        }
    }
}
