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

    @Mapping(target = "projectId", source = "idProject", qualifiedByName = "toUUID")
    @Mapping(target = "finalAmount", source = "contract.budget")
    @Mapping(target = "numberPayment", source = "contract.numberPayment")
    @Mapping(target = "description", source = "contract.description")
    CreateContractRq toCreateContractRq(ContractPayload contract, String idProject);

    @Named("toUUID")
    public static UUID toUUID(String id) {
        return UUID.fromString(id);
    }

    @Mapping(target = "contractId", source = "readContractRs.contract_id")
    @Mapping(target = "budget", source = "readContractRs.final_amount")
    @Mapping(target = "numberPayment", source = "readContractRs.number_payments")
    @Mapping(target = "description", source = "readContractRs.description")
    @Mapping(target = "numberPaymentPaid", source = "numberPaymentPaid")
    @Mapping(target = "status", source = "readContractRs.status")
    @Mapping(target = "fileUrl", source = "readContractRs.file_url")
    ContractPayload toDetailsContractRs(ReadContractRs readContractRs, Integer numberPaymentPaid);
}
