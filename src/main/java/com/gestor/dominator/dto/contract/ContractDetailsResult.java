package com.gestor.dominator.dto.contract;

public record ContractDetailsResult(
        String id,
        String status,
        String fileUrl,
        String signedAt,
        String finalAmount,
        String numberPayments,
        String description,
        String projectId,
        String createdAt,
        String updatedAt) {

}
