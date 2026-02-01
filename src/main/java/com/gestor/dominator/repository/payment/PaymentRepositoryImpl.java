package com.gestor.dominator.repository.payment;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.gestor.dominator.constants.PaymentType;
import com.gestor.dominator.exceptions.custom.PostgreDbException;
import com.gestor.dominator.model.postgre.payment.PaymentCreateRq;
import com.gestor.dominator.model.postgre.payment.PaymentCreateRs;
import com.gestor.dominator.model.postgre.payment.PaymentReadRs;

import static com.gestor.dominator.repository.payment.PaymentQueryBD.*;

import java.util.List;
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
                paymentCreateRq.type(), paymentCreateRq.amount(), paymentCreateRq.contract_id());

        if (paymentId == null) {
            throw new PostgreDbException("Payment not created");
        }

        return new PaymentCreateRs(paymentId.toString());
    }

    @Override
    public List<PaymentReadRs> getPayments(UUID contractId) {
        List<PaymentReadRs> payments = jdbcTemplate.query(GET_PAYMENTS,
                (rs, rowNum) -> new PaymentReadRs(
                        rs.getString("payment_id"),
                        rs.getDouble("amount"),
                        rs.getDate("payment_date") != null ? rs.getDate("payment_date").toLocalDate() : null,
                        rs.getString("type"),
                        rs.getBoolean("paid")),
                contractId);

        if (payments.isEmpty()) {
            throw new PostgreDbException("Payments not found");
        }

        return payments;
    }

    @Override
    public void validateFirstPaymentExists(UUID contractId) {
        int existsPayment = jdbcTemplate.queryForObject(EXISTS_PAYMENT_BY_TYPE, Integer.class,
                PaymentType.FIRST.getValue(), contractId);

        if (existsPayment == 0) {
            throw new PostgreDbException("You must create the first payment");
        }
    }

}
