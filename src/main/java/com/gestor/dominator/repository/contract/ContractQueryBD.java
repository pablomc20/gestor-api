package com.gestor.dominator.repository.contract;

public final class ContractQueryBD {

    private ContractQueryBD() {
    }

    public static final String CREATE_CONTRACT = """
            INSERT INTO contracts (
                final_amount, project_id, number_payments, description
            )
            VALUES (?, ?, ?, ?)
            RETURNING contract_id""";

    public static final String READ_CONTRACT_BY_PROJECT_ID = """
            SELECT * FROM contracts WHERE project_id = ?""";

}
