package com.gestor.dominator.mapper;

import java.util.UUID;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.gestor.dominator.dto.projects.ContractPayload;
import com.gestor.dominator.model.postgre.contract.CreateContractRq;
import com.gestor.dominator.model.postgre.contract.ReadContractRs;

@Mapper(componentModel = "spring")
public interface ContractMapper {

    @Mapping(target = "project_id", source = "idProject", qualifiedByName = "toUUID")
    @Mapping(target = "final_amount", source = "contract.budget")
    @Mapping(target = "number_payment", source = "contract.number_payment")
    @Mapping(target = "description", source = "contract.description")
    CreateContractRq toCreateContractRq(ContractPayload contract, String idProject);

    @Named("toUUID")
    public static UUID toUUID(String id) {
        return UUID.fromString(id);
    }

    @Mapping(target = "contractId", source = "readContractRs.contract_id")
    @Mapping(target = "budget", source = "readContractRs.final_amount")
    @Mapping(target = "number_payment", source = "readContractRs.number_payments")
    @Mapping(target = "description", source = "readContractRs.description")
    @Mapping(target = "number_payment_paid", source = "numberPaymentPaid")
    ContractPayload toDetailsContractRs(ReadContractRs readContractRs, Integer numberPaymentPaid);
}
