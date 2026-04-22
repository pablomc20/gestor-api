package com.gestor.dominator.components;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.gestor.dominator.exceptions.custom.FileSystemException;
import com.gestor.dominator.utils.FileUtils;

import net.coobird.thumbnailator.Thumbnails;

@Component
public class ThumbnailsConverter {

    public BufferedImage getOriginal(MultipartFile file) {
        try {
            return ImageIO.read(new ByteArrayInputStream(file.getBytes()));
        } catch (IOException e) {
            throw new FileSystemException("No se pudo leer la imagen", e);
        }
    }

    public String generateIdentifier() {
        return String.valueOf(System.currentTimeMillis());
    }

    public String generateFilename(MultipartFile file, String identifier) {
        return FileUtils.generateUniqueFilename(file.getOriginalFilename(), identifier);
    }

    public String buildOrigObjectName(String identifier, String filename, String extension) {
        return identifier + "/" + filename + "_orig" + extension;
    }

    public String buildMedObjectName(String identifier, String filename, String extension) {
        return identifier + "/" + filename + "_med" + extension;
    }

    public String buildThumbObjectName(String identifier, String filename, String extension) {
        return identifier + "/" + filename + "_thumb" + extension;
    }

    public byte[] formatImageMed(BufferedImage original, String format) {
        // Versión mediana (ej. 1280px ancho máx)
        ByteArrayOutputStream medOutput = new ByteArrayOutputStream();
        try {
            Thumbnails.of(original)
                    .size(1280, 1280)
                    .outputQuality(0.8)
                    .outputFormat(format)
                    .toOutputStream(medOutput);
        } catch (IOException e) {
            throw new FileSystemException("Error al formatear la imagen (mediana)", e);
        }
        return medOutput.toByteArray();
    }

    public byte[] formatImageThumb(BufferedImage original, String format) {
        // Miniatura (ej. 300px ancho máx)
        ByteArrayOutputStream thumbOutput = new ByteArrayOutputStream();
        try {
            Thumbnails.of(original)
                    .size(300, 300)
                    .outputQuality(0.7)
                    .outputFormat(format)
                    .toOutputStream(thumbOutput);
        } catch (IOException e) {
            throw new FileSystemException("Error al formatear la imagen (miniatura)", e);
        }
        return thumbOutput.toByteArray();
    }
}
