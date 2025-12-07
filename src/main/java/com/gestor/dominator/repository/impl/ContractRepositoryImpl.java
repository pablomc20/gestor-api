package com.gestor.dominator.repository.impl;

import com.gestor.dominator.components.ObjectManipulationUtil;
import com.gestor.dominator.exceptions.custom.PostgreDbException;
import com.gestor.dominator.model.postgre.contract.CreateContractRq;
import com.gestor.dominator.model.postgre.contract.CreateContractRs;
import com.gestor.dominator.repository.ContractRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ContractRepositoryImpl implements ContractRepository {

  private final JdbcTemplate jdbcTemplate;
  private final ObjectManipulationUtil objectManipulationUtil;

  @Override
  public CreateContractRs createContract(CreateContractRq createContractRecord) {
    String sql = "SELECT fn_create_contract(?, ?, ?)";

    String jsonResult = jdbcTemplate.queryForObject(sql, String.class,
        createContractRecord.final_amount(), createContractRecord.project_id(),
        createContractRecord.number_payment());

    CreateContractRs createContractRs = objectManipulationUtil
        .objectMapperToString(jsonResult, CreateContractRs.class);

    if ("error".equals(createContractRs.status())) {
      throw new PostgreDbException(createContractRs.error_code() + " - " + createContractRs.error_message());
    }

    return createContractRs;
  }

}
