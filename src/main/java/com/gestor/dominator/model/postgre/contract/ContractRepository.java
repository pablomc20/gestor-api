package com.gestor.dominator.model.postgre.contract;

public interface ContractRepository {
  CreateContractRs createContract(CreateContractRq createContractRecord);

  ReadContractRs getContractDetailsById(ReadContractRq contractDetailsRq);
}
