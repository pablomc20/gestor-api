package com.gestor.dominator.components;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.gestor.dominator.client.MinioStorageService;
import com.gestor.dominator.dto.image.ImageRenderResponse;
import com.gestor.dominator.exceptions.custom.DataValidationException;
import com.gestor.dominator.exceptions.custom.FileSystemException;
import com.gestor.dominator.utils.FileUtils;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FileStorageComponent {

    private final MinioStorageService minioStorageService;

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

        // Lee y registra el archivo en el conversor de miniaturas
        thumbnailsConverter.registerFile(file);

        // Subir original
        String origObjectName = thumbnailsConverter.buildOrigObjectName();
        minioStorageService.uploadFile(file, origObjectName);

        // Subir mediana y miniatura
        byte[] medBytes = thumbnailsConverter.formatImageMed();
        String medObjectName = thumbnailsConverter.buildMedObjectName();
        minioStorageService.uploadFile(medBytes, medObjectName, file.getContentType());

        byte[] thumbBytes = thumbnailsConverter.formatImageThumb();
        String thumbObjectName = thumbnailsConverter.buildThumbObjectName();
        minioStorageService.uploadFile(thumbBytes, thumbObjectName, file.getContentType());

        String filename = thumbnailsConverter.getFilename();
        thumbnailsConverter.clear(); // Limpiar el estado del conversor

        return filename;
    }

    /**
     * Elimina un archivo de Minio.
     *
     * @param filename El nombre base del archivo a eliminar.
     * @param ext La extensión del archivo.
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
     * @param filename El nombre del archivo a cargar (incluye sufijo como _orig, _med, _thumb).
     * @return Un objeto con los bytes del archivo y su tipo de contenido.
     */
    public ImageRenderResponse loadFile(String filename) {
        return minioStorageService.downloadFile(FileUtils.generateObjectName(filename));
    }

}
