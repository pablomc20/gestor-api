package com.gestor.dominator.mapper;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Comparator;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.gestor.dominator.dto.payment.CreatePaymentRecord;
import com.gestor.dominator.dto.projects.PaymentPayload;
import com.gestor.dominator.model.postgre.payment.PaymentCreateRq;
import com.gestor.dominator.model.postgre.payment.PaymentReadRs;
import com.gestor.dominator.service.projects.ReadPaymentResult;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    // Aplica a un pago individual
    @Mapping(target = "contract_id", source = "rq.idContract")
    @Mapping(target = "type", source = "rq.type")
    @Mapping(target = "amount", source = "rq.amount")
    @Mapping(target = "payment_date", source = "rq.paymentDate")
    PaymentCreateRq toPaymentCreateRq(CreatePaymentRecord rq);

    @Mapping(target = "paymentDate", source = "paymentReadRs.payment_date")
    @Mapping(target = "type", source = "paymentReadRs.type")
    @Mapping(target = "amount", source = "paymentReadRs.amount")
    @Mapping(target = "paymentId", source = "paymentReadRs.payment_id")
    @Mapping(target = "paid", source = "paymentReadRs.paid")
    ReadPaymentResult toPaymentReadedRs(PaymentReadRs paymentReadRs);

    List<ReadPaymentResult> toPaymentReadedRs(List<PaymentReadRs> paymentReadRs);

    // Aplica para varios pagos, cuando se crea un contrato
    @Mapping(target = "contract_id", source = "idContract")
    @Mapping(target = "type", source = "payment.type")
    @Mapping(target = "amount", source = "payment.amount")
    @Mapping(target = "payment_date", ignore = true)
    PaymentCreateRq toPaymentCreateRq(PaymentPayload payment, UUID idContract);

    @Mapping(target = "isPaid", source = "paymentReadRs.paid")
    @Mapping(target = "paymentId", source = "paymentReadRs.payment_id")
    @Mapping(target = "paymentDate", source = "paymentReadRs.payment_date")
    @Mapping(target = "type", source = "paymentReadRs.type")
    @Mapping(target = "amount", source = "paymentReadRs.amount")
    PaymentPayload toPayload(PaymentReadRs paymentReadRs);

    PaymentPayload[] toPayloads(List<PaymentReadRs> paymentReadRs);

}
