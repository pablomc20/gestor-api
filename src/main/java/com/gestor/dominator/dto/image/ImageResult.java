package com.gestor.dominator.dto.image;

import lombok.Builder;
import java.time.format.DateTimeFormatter;

import com.gestor.dominator.model.postgre.image.ImageRs;

@Builder
public record ImageResult(
        String id,
        String filePath,
        String medPath,
        String thumbPath,
        long size,
        String mimeType,
        String createdAt) {
    public static ImageResult fromModel(ImageRs imageRs) {
        // Define el formato deseado
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        // Retorna el string formateado
        return new ImageResult(
                imageRs.id(),
                "/file/" + imageRs.filename() + "_orig" + imageRs.ext(),
                "/file/" + imageRs.filename() + "_med" + imageRs.ext(),
                "/file/" + imageRs.filename() + "_thumb" + imageRs.ext(),
                imageRs.size(),
                imageRs.mimeType(),
                formatter.format(imageRs.createdAt()));
    }
}
