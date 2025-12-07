package com.gestor.dominator.model.postgre.contract;

public record CreateContractResult(
    String status,
    String contract_id,
    String error_code,
    String error_message) {

}
