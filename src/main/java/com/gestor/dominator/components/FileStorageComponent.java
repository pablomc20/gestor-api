package com.gestor.dominator.components;

import java.awt.image.BufferedImage;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.gestor.dominator.client.MinioStorageClient;
import com.gestor.dominator.exceptions.custom.DataValidationException;
import com.gestor.dominator.exceptions.custom.FileSystemException;
import com.gestor.dominator.model.client.minio.ImageRenderRs;
import com.gestor.dominator.utils.FileUtils;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FileStorageComponent {

    private final MinioStorageClient minioStorageService;

    private final ThumbnailsConverter thumbnailsConverter;

    /**
     * Guarda un archivo en Minio y devuelve el nombre único generado.
     *
     * @param file El archivo a guardar.
     * @return El nombre único del archivo guardado.
     */
    public String save(MultipartFile file) {
        if (!FileUtils.isValidImageType(file.getContentType())) {
            throw new DataValidationException("Tipo de archivo no válido. Solo se permiten imágenes.");
        }

        String contentType = file.getContentType();
        String format = FileUtils.getFormatFromContentType(contentType);
        String extension = FileUtils.getFileExtension(file.getOriginalFilename());
        
        String identifier = thumbnailsConverter.generateIdentifier();
        String filename = thumbnailsConverter.generateFilename(file, identifier);
        BufferedImage original = thumbnailsConverter.getOriginal(file);

        // Subir original
        String origObjectName = thumbnailsConverter.buildOrigObjectName(identifier, filename, extension);
        minioStorageService.uploadFile(file, origObjectName);

        // Subir mediana y miniatura
        byte[] medBytes = thumbnailsConverter.formatImageMed(original, format);
        String medObjectName = thumbnailsConverter.buildMedObjectName(identifier, filename, extension);
        minioStorageService.uploadFile(medBytes, medObjectName, contentType);

        byte[] thumbBytes = thumbnailsConverter.formatImageThumb(original, format);
        String thumbObjectName = thumbnailsConverter.buildThumbObjectName(identifier, filename, extension);
        minioStorageService.uploadFile(thumbBytes, thumbObjectName, contentType);

        return filename;
    }

    /**
     * Elimina un archivo de Minio.
     *
     * @param filename El nombre base del archivo a eliminar.
     * @param ext      La extensión del archivo.
     */
    public void delete(String filename, String ext) {
        try {
            // Eliminar las tres versiones del archivo
            String objectName = FileUtils.generateObjectName(filename);
            minioStorageService.deleteFile(objectName + "_orig" + ext);
            minioStorageService.deleteFile(objectName + "_med" + ext);
            minioStorageService.deleteFile(objectName + "_thumb" + ext);
        } catch (Exception e) {
            System.err.println("Error al eliminar el archivo de Minio: " + filename + " - " + e.getMessage());
            throw new FileSystemException("Error al eliminar el archivo de Minio", e);
        }
    }

    /**
     * Carga un archivo desde Minio como un recurso para ser servido.
     *
     * @param filename El nombre del archivo a cargar (incluye sufijo como _orig,
     *                 _med, _thumb).
     * @return Un objeto con los bytes del archivo y su tipo de contenido.
     */
    public ImageRenderRs loadFile(String filename) {
        return minioStorageService.downloadFile(FileUtils.generateObjectName(filename));
    }

}
