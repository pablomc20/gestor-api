package com.gestor.dominator.dto.projects;

import java.time.LocalDate;

import lombok.Builder;

@Builder
public record ProjectPayload(
        String projectId,
        String title,
        String style,
        String size,
        String category,
        String status,
        String chapes,
        String colors,
        String materials,
        String additionals,
        LocalDate startDate,
        LocalDate endDate) {

}
