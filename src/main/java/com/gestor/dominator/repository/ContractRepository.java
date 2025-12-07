package com.gestor.dominator.repository;

import com.gestor.dominator.model.postgre.contract.CreateContractRecord;
import com.gestor.dominator.model.postgre.contract.CreateContractResult;

public interface ContractRepository {
  CreateContractResult createContract(CreateContractRecord createContractRecord);
}
