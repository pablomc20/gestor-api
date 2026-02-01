package com.gestor.dominator.dto.projects;

import lombok.Builder;

@Builder
public record ProjectDetailsResult(
        ProjectPayload project,
        ContractPayload contract,
        PaymentPayload[] payments) {

}
