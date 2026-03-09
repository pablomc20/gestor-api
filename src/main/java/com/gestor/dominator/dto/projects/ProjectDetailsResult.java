package com.gestor.dominator.dto.projects;

import java.util.List;

import lombok.Builder;

@Builder
public record ProjectDetailsResult(
        ProjectPayload project,
        ContractPayload contract,
        PaymentPayload[] payments,
        List<UserPayload> users) {

}
