package com.gestor.dominator.model.postgre.project;

import java.time.LocalDate;

import lombok.Builder;

@Builder
public record ProjectPayload(
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
