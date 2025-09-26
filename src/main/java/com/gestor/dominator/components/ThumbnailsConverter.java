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

    private MultipartFile file;
    
    private BufferedImage original;
    
    private String format;

    private String extension;

    private String filename;

    private String identifier;

    public void registerFile(MultipartFile file) {
        this.file = file;
    }

    public void clear() {
        this.file = null;
        this.original = null;
        this.format = null;
        this.extension = null;
        this.filename = null;
        this.identifier = null;
    }

    private BufferedImage getOriginal() {
        if (this.original == null) {
            try {
                this.original = ImageIO.read(new ByteArrayInputStream(file.getBytes()));
            } catch (IOException e) {
                throw new FileSystemException("No se pudo leer la imagen", e);
            }
        }
        return this.original;
    }

    private String getFormat() {
        if (this.format == null) {
            this.format = FileUtils.getFormatFromContentType(file.getContentType());
        }
        return this.format;
    }

    private String getExtension() {
        if (this.extension == null) {
            this.extension = FileUtils.getFileExtension(file.getOriginalFilename());
        }
        return this.extension;
    }

    public String getFilename() {
        if (this.filename == null) {
            String identifier = getIdentifier();
            this.filename = FileUtils.generateUniqueFilename(file.getOriginalFilename(), identifier);
        }
        return this.filename;
    }

    private String getIdentifier() {
        if (this.identifier == null) {
            this.identifier = String.valueOf(System.currentTimeMillis());
        }
        return this.identifier;
    }

    public String buildOrigObjectName() {
        return getIdentifier() + "/" + getFilename() + "_orig" + getExtension();
    }

    public String buildMedObjectName() {
        return getIdentifier() + "/" + getFilename() + "_med" + getExtension();
    }

    public String buildThumbObjectName() {
        return getIdentifier() + "/" + getFilename() + "_thumb" + getExtension();
    }

    public byte[] formatImageMed() {
        // Versión mediana (ej. 1280px ancho máx)
        ByteArrayOutputStream medOutput = new ByteArrayOutputStream();
        try {
            Thumbnails.of(getOriginal())
                    .size(1280, 1280)
                    .outputQuality(0.8)
                    .outputFormat(getFormat())
                    .toOutputStream(medOutput);
        } catch (IOException e) {
            throw new FileSystemException("Error al formatear la imagen", e);
        }
        return medOutput.toByteArray();
    }

    public byte[] formatImageThumb() {
        // Miniatura (ej. 300px ancho máx)
        ByteArrayOutputStream thumbOutput = new ByteArrayOutputStream();
        try {
            Thumbnails.of(getOriginal())
                    .size(300, 300)
                    .outputQuality(0.7)
                    .outputFormat(getFormat())
                    .toOutputStream(thumbOutput);
        } catch (IOException e) {
            throw new FileSystemException("Error al formatear la imagen", e);
        }
        return thumbOutput.toByteArray();
    }
}
