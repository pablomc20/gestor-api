package com.gestor.dominator.dto.projects.usecase;

import java.util.List;

import lombok.Builder;

@Builder
public record ProjectListPublicResult(
    String projectId,
    String title,
    String style,
    String size,
    String category,
    String status,
    String chapes,
    String materials,
    String additionals,
    String startDate,
    String endDate,
    String realEndDate,
    List<GalleryImagePayload> gallery
) {

}
