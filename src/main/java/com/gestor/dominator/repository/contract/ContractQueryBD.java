package com.gestor.dominator.repository.contract;

public final class ContractQueryBD {

    private ContractQueryBD() {
    }

    public static final String CREATE_CONTRACT = "SELECT fn_create_contract(?, ?, ?, ?)";

}
