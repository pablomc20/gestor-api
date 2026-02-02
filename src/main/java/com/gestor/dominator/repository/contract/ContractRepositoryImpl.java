package com.gestor.dominator.repository.contract;

import com.gestor.dominator.exceptions.custom.PostgreDbException;
import com.gestor.dominator.model.postgre.contract.ContractRepository;
import com.gestor.dominator.model.postgre.contract.CreateContractRq;
import com.gestor.dominator.model.postgre.contract.CreateContractRs;
import com.gestor.dominator.model.postgre.contract.ReadContractRq;
import com.gestor.dominator.model.postgre.contract.ReadContractRs;

import static com.gestor.dominator.repository.contract.ContractQueryBD.CREATE_CONTRACT;

import java.time.LocalDate;
import java.util.UUID;

import lombok.RequiredArgsConstructor;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ContractRepositoryImpl implements ContractRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public CreateContractRs createContract(CreateContractRq createContractRecord) {
        UUID contractId = jdbcTemplate.queryForObject(CREATE_CONTRACT, UUID.class,
                createContractRecord.finalAmount(), createContractRecord.projectId(),
                createContractRecord.numberPayment(), createContractRecord.description());

        if (contractId == null) {
            throw new PostgreDbException("Error al crear el contrato");
        }

        return new CreateContractRs(contractId);
    }

    @Override
    public ReadContractRs getContractDetailsById(ReadContractRq contractDetailsRq) {

        UUID projectId = contractDetailsRq.project_id();

        return jdbcTemplate.queryForObject(
                ContractQueryBD.READ_CONTRACT_BY_PROJECT_ID,
                (rs, rowNum) -> ReadContractRs.builder()
                        .contract_id(rs.getString("contract_id"))
                        .status(rs.getString("status"))
                        .file_url(rs.getString("file_url"))
                        .signed_at(rs.getObject("signed_at", LocalDate.class))
                        .final_amount(rs.getBigDecimal("final_amount"))
                        .number_payments(rs.getInt("number_payments"))
                        .description(rs.getString("description"))
                        .build(),
                projectId);
    }

}
