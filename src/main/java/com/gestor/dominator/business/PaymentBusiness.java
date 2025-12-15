package com.gestor.dominator.business;

import org.springframework.stereotype.Service;

import com.gestor.dominator.dto.payment.CreatePaymentRecord;
import com.gestor.dominator.dto.payment.CreatePaymentResult;
import com.gestor.dominator.dto.projects.CreateProjectRecord;
import com.gestor.dominator.dto.projects.CreateProjectResult;
import com.gestor.dominator.mapper.PaymentMapper;
import com.gestor.dominator.model.postgre.payment.PaymentCreateRq;
import com.gestor.dominator.model.postgre.payment.PaymentCreateRs;
import com.gestor.dominator.repository.payment.PaymentRepository;
import com.gestor.dominator.service.projects.PaymentService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentBusiness implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;

    @Override
    public CreatePaymentResult createPayment(CreatePaymentRecord createPaymentRecord) {
        PaymentCreateRq paymentCreateRecord = paymentMapper.toPaymentRq(createPaymentRecord);

        // Crear pago en la BD
        PaymentCreateRs paymentCreateRs = paymentRepository.createPayment(paymentCreateRecord);

        return CreatePaymentResult.builder()
                .idPayment(paymentCreateRs.payment_id())
                .build();
    }

}
