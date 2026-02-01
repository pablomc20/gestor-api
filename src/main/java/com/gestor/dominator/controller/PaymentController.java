package com.gestor.dominator.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gestor.dominator.dto.payment.CreatePaymentRecord;
import com.gestor.dominator.dto.payment.CreatePaymentResult;
import com.gestor.dominator.service.projects.PaymentService;
import com.gestor.dominator.service.projects.ReadPaymentResult;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<CreatePaymentResult> createPayment(@RequestBody CreatePaymentRecord createPaymentRecord) {
        return ResponseEntity.ok(paymentService.createPayment(createPaymentRecord));
    }

    @GetMapping(value = "/contract/{contractId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ReadPaymentResult> getPaymentsByContractId(@PathVariable UUID contractId) {
        return paymentService.getPaymentsByContractId(contractId);
    }
}
