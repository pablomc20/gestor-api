package com.gestor.dominator.mapper;

import java.util.UUID;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.gestor.dominator.dto.payment.CreatePaymentRecord;
import com.gestor.dominator.model.postgre.payment.PaymentCreateRq;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    @Mapping(target = "contract_id", source = "rq.idContract", qualifiedByName = "toUUID")
    @Mapping(target = "payment_date", source = "rq.paymentDate")
    @Mapping(target = "type", source = "rq.type")
    @Mapping(target = "amount", source = "rq.amount")
    PaymentCreateRq toPaymentRq(CreatePaymentRecord rq);

    @Named("toUUID")
    public static UUID toUUID(String id) {
        return UUID.fromString(id);
    }
}
