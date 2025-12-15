package com.gestor.dominator.repository.payment;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.gestor.dominator.exceptions.custom.PostgreDbException;
import com.gestor.dominator.model.postgre.payment.PaymentCreateRq;
import com.gestor.dominator.model.postgre.payment.PaymentCreateRs;
import static com.gestor.dominator.repository.payment.PaymentQueryBD.*;

import java.util.UUID;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PaymentRepositoryImpl implements PaymentRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public PaymentCreateRs createPayment(PaymentCreateRq paymentCreateRq) {

        Integer existsPayment = jdbcTemplate.queryForObject(GET_PAYMENT_BY_TYPE, Integer.class,
                paymentCreateRq.type(), paymentCreateRq.contract_id());

        if (existsPayment > 0) {
            throw new PostgreDbException("Payment already exists");
        }

        UUID paymentId = jdbcTemplate.queryForObject(CREATE_PAYMENT, UUID.class,
                paymentCreateRq.type(), paymentCreateRq.amount(), paymentCreateRq.contract_id(),
                paymentCreateRq.payment_date());

        if (paymentId == null) {
            throw new PostgreDbException("Payment not created");
        }

        return new PaymentCreateRs(paymentId.toString());
    }

}
