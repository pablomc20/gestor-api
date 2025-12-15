package com.gestor.dominator.repository.contract;

import com.fasterxml.jackson.core.type.TypeReference;
import com.gestor.dominator.components.ObjectManipulationUtil;
import com.gestor.dominator.exceptions.custom.PostgreDbException;
import com.gestor.dominator.model.postgre.DbResult;
import com.gestor.dominator.model.postgre.contract.CreateContractRq;
import com.gestor.dominator.model.postgre.contract.CreateContractRs;
import static com.gestor.dominator.repository.contract.ContractQueryBD.CREATE_CONTRACT;

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
    String jsonResult = jdbcTemplate.queryForObject(CREATE_CONTRACT, String.class,
        createContractRecord.final_amount(), createContractRecord.project_id(),
        createContractRecord.number_payment(), createContractRecord.description());

    DbResult<CreateContractRs> createContractRs = objectManipulationUtil
        .fromJson(jsonResult, new TypeReference<DbResult<CreateContractRs>>() {
        });

    if (!createContractRs.isOk()) {
      throw new PostgreDbException(
          createContractRs.error().code() + " - " + createContractRs.error().message());
    }

    return createContractRs.data();
  }

}
