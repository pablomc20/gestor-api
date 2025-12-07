package com.gestor.dominator.repository.impl;

import com.gestor.dominator.components.ObjectManipulationUtil;
import com.gestor.dominator.exceptions.custom.PostgreDbException;
import com.gestor.dominator.model.postgre.contract.CreateContractRecord;
import com.gestor.dominator.model.postgre.contract.CreateContractResult;
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
  public CreateContractResult createContract(CreateContractRecord createContractRecord) {
    String sql = "SELECT fn_create_contract(?, ?, ?)";

    String jsonResult = jdbcTemplate.queryForObject(sql, String.class,
        createContractRecord.final_amount(), createContractRecord.project_id(),
        createContractRecord.number_payment());

    CreateContractResult createContractResult = objectManipulationUtil
        .objectMapperToString(jsonResult, CreateContractResult.class);

    if ("error".equals(createContractResult.status())) {
      throw new PostgreDbException(createContractResult.error_code() + " - " + createContractResult.error_message());
    }

    return createContractResult;
  }

}
