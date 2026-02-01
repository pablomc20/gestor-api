package com.gestor.dominator.service.contract;

import java.util.UUID;

import com.gestor.dominator.dto.contract.ContractDetailsResult;

public interface ContractService {
    ContractDetailsResult getContractDetailsById(UUID contractId);
}
