package com.gestor.dominator.model.postgre.project;

import java.time.LocalDate;
import java.util.List;

import lombok.Builder;

@Builder
public record ProjectListPublicRs(
    String projectId,
    String title,
    String style,
    String size,
    String category,
    String status,
    String chapes,
    String materials,
    String additionals,
    LocalDate startDate,
    LocalDate endDate,
    LocalDate realEndDate,
    List<GalleryImageDto> gallery
) {
    
}
