package com.gestor.dominator.model.postgre.contract;

public record CreateContractRs(
        String status,
        String contract_id,
        String error_code,
        String error_message) {

}
