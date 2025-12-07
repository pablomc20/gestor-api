package com.gestor.dominator.mapper;

import java.util.UUID;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.gestor.dominator.dto.projects.CreateProjectRecord;
import com.gestor.dominator.model.postgre.contract.CreateContractRq;

@Mapper(componentModel = "spring")
public interface ContractMapper {

  @Mapping(target = "project_id", source = "idProject", qualifiedByName = "toUUID")
  @Mapping(target = "final_amount", source = "rq.budget")
  @Mapping(target = "number_payment", source = "rq.number_payment")
  CreateContractRq toRecord(CreateProjectRecord rq, String idProject);

  @Named("toUUID")
  public static UUID toUUID(String id) {
    return UUID.fromString(id);
  }
}
