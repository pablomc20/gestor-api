package com.gestor.dominator.components;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.gestor.dominator.dto.image.ImageRenderResponse;
import com.gestor.dominator.exceptions.custom.DataValidationException;
import com.gestor.dominator.exceptions.custom.FileSystemException;

import jakarta.annotation.PostConstruct;

@Component
public class FileStorageComponent {
    
    @Value("${app.upload.dir:uploads/images}")
    private String uploadDir;

    private Path uploadPath;

    @PostConstruct
    public void init() {
        try {
            this.uploadPath = Paths.get(uploadDir).toAbsolutePath();
            Files.createDirectories(uploadPath);
            System.out.println("Directorio de uploads creado exitosamente: " + uploadPath);
        } catch (IOException e) {
            System.err.println("Error al crear el directorio de uploads: " + uploadPath + " - " + e.getMessage());
            throw new FileSystemException("No se pudo crear el directorio de uploads: " + uploadDir, e);
        }
    }

    /**
     * Guarda un archivo en el disco y devuelve el nombre único generado.
     * @param file El archivo a guardar.
     * @return El nombre único del archivo guardado.
     */
    public String save(MultipartFile file) {
        if (!isValidImageType(file.getContentType())) {
            throw new DataValidationException("Tipo de archivo no válido. Solo se permiten imágenes.");
        }

        String filename = generateUniqueFilename(file.getOriginalFilename());
        try {
            Path filePath = uploadPath.resolve(filename);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            return filename;
        } catch (IOException e) {
            throw new FileSystemException("Error al guardar el archivo en el servidor", e);
        }
    }

    /**
     * Elimina un archivo del disco.
     * @param filename El nombre del archivo a eliminar.
     */
    public void delete(String filename) {
        try {
            Path filePath = uploadPath.resolve(filename);
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            // Se podría loggear el error pero no relanzar la excepción para no detener el flujo si solo falla el borrado físico
            System.err.println("Error al eliminar el archivo físico: " + filename + " - " + e.getMessage());
            throw new FileSystemException("Error al eliminar el archivo físico", e);
        }
    }

    /**
     * Carga un archivo como un recurso para ser servido.
     * @param filename El nombre del archivo a cargar.
     * @return Un objeto con los bytes del archivo y su tipo de contenido.
     */
    public ImageRenderResponse loadFile(String filename) {
        try {
            Path filePath = this.uploadPath.resolve(filename);
            if (!Files.exists(filePath) || !Files.isReadable(filePath)) {
                throw new DataValidationException("El archivo de imagen no existe o no se puede leer");
            }

            byte[] imageBytes = Files.readAllBytes(filePath);
            String contentType = Files.probeContentType(filePath);
            if (contentType == null) {
                contentType = "application/octet-stream";
            }

            return new ImageRenderResponse(imageBytes, contentType);
        } catch (IOException e) {
            throw new FileSystemException("Error al leer el archivo de imagen", e);
        }
    }

    private boolean isValidImageType(String contentType) {
        return contentType != null && (
                contentType.equals("image/jpeg") ||
                contentType.equals("image/png") ||
                contentType.equals("image/gif") ||
                contentType.equals("image/webp")
        );
    }

    private String generateUniqueFilename(String originalFilename) {
        String extension = getFileExtension(originalFilename);
        return "imageFile-" + System.currentTimeMillis() + "-" + UUID.randomUUID().toString().substring(0, 8) + extension;
    }

    private String getFileExtension(String filename) {
        if (filename == null || filename.lastIndexOf('.') == -1) {
            return "";
        }
        return filename.substring(filename.lastIndexOf('.'));
    }
    
}
