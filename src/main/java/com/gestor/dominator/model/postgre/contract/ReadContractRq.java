package com.gestor.dominator.model.postgre.contract;

import java.util.UUID;

import lombok.Builder;

@Builder
public record ReadContractRq(
        UUID project_id) {

}
