package com.gestor.dominator.model.postgre.project;

import java.time.LocalDate;

import lombok.Builder;

@Builder
public record ProjectPayload(
        String user_client,
        String user_employee,
        String title,
        String style,
        String size,
        String category,
        String status,
        String chapes,
        String colors,
        String materials,
        String additionals,
        LocalDate start_date,
        LocalDate end_date) {

}
