package com.gestor.dominator.repository.payment;

public final class PaymentQueryBD {

    private PaymentQueryBD() {
    }

    public static final String CREATE_PAYMENT = """
                INSERT INTO payments (type, amount, project_id)
                VALUES (?::payment_type, ?, ?::uuid)
                RETURNING payment_id
            """;

    public static final String GET_PAYMENT_BY_TYPE = """
                SELECT COUNT(*)
                FROM payments
                WHERE type = ?::payment_type
                AND project_id = ?::uuid
            """;

    public static final String GET_PAYMENTS = """
                SELECT payment_id, amount, payment_date, type, paid
                FROM payments
                WHERE project_id = ?::uuid
            """;

    public static final String EXISTS_PAYMENT_BY_TYPE = """
                SELECT COUNT(*)
                FROM payments
                WHERE type = ?::payment_type
                AND project_id = ?::uuid
            """;
}
