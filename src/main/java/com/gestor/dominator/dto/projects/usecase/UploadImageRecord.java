package com.gestor.dominator.dto.projects.usecase;

public record UploadImageRecord(
        String projectId,
        String imageId,
        String type,
        String visibility) {

}
