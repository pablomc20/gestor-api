package com.gestor.dominator.model.postgre.project;

import java.time.LocalDate;

import lombok.Builder;

@Builder
public record DetailsForClientRs(
        String title,
        LocalDate started,
        LocalDate estimated,
        Integer days_difference,
        String name,
        String project_id,
        String status,
        String user_id,
        String type_desc) {

}
