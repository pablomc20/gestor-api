package com.gestor.dominator.repository.payment;

public final class PaymentQueryBD {

    private PaymentQueryBD() {
    }

    public static final String CREATE_PAYMENT = """
                INSERT INTO payments (type, amount, contract_id, payment_date)
                VALUES (?::payment_type, ?, ?::uuid, ?::date)
                RETURNING payment_id
            """;

    public static final String GET_PAYMENT_BY_TYPE = """
                SELECT COUNT(*)
                FROM payments
                WHERE type = ?::payment_type
                AND contract_id = ?::uuid
            """;
}
