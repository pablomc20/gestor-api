package com.gestor.dominator.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gestor.dominator.dto.payment.CreatePaymentRecord;
import com.gestor.dominator.dto.payment.CreatePaymentResult;
import com.gestor.dominator.service.projects.PaymentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/projects/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<CreatePaymentResult> createPayment(@RequestBody CreatePaymentRecord createPaymentRecord) {
        return ResponseEntity.ok(paymentService.createPayment(createPaymentRecord));
    }
}
