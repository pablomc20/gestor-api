package com.gestor.dominator.repository;

import com.gestor.dominator.model.postgre.contract.CreateContractRq;
import com.gestor.dominator.model.postgre.contract.CreateContractRs;

public interface ContractRepository {
  CreateContractRs createContract(CreateContractRq createContractRecord);
}
