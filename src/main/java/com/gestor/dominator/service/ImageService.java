package com.gestor.dominator.service;

import com.gestor.dominator.dto.ImageResponse;
import com.gestor.dominator.dto.ImageUpdateRequest;
import com.gestor.dominator.exceptions.custom.DataValidationException;
import com.gestor.dominator.exceptions.custom.FileSystemException;
import com.gestor.dominator.model.Image;
import com.gestor.dominator.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

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

    public List<ImageResponse> getAllImages() {
        return imageRepository.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public Optional<ImageResponse> getImageById(String id) {
        return imageRepository.findById(id)
                .map(this::convertToResponse);
    }

    public ImageResponse uploadImage(MultipartFile file) {
        // Validar tipo de archivo
        if (!isValidImageType(file.getContentType())) {
            throw new DataValidationException("Tipo de archivo no válido. Solo se permiten imágenes.");
        }

        // Generar nombre único
        String filename = generateUniqueFilename(file.getOriginalFilename());

        try {
            // Guardar archivo en disco
            Path filePath = uploadPath.resolve(filename);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // Crear entidad Image
            Image image = new Image(filename, file.getOriginalFilename(), file.getSize(), file.getContentType());
            Image savedImage = imageRepository.save(image);

            return convertToResponse(savedImage);
        } catch (IOException e) {
            throw new FileSystemException("Error al guardar el archivo en el servidor", e);
        }
    }

    public Optional<ImageResponse> updateImage(String id, ImageUpdateRequest request) {
        return imageRepository.findById(id)
                .map(image -> {
                    if (request.getOriginalName() != null) {
                        image.setOriginalName(request.getOriginalName());
                    }
                    Image updatedImage = imageRepository.save(image);
                    return convertToResponse(updatedImage);
                });
    }

    public boolean deleteImage(String id) {
        return imageRepository.findById(id)
                .map(image -> {
                    try {
                        // Eliminar archivo físico
                        Path filePath = uploadPath.resolve(image.getFilename());
                        Files.deleteIfExists(filePath);

                        // Eliminar de base de datos
                        imageRepository.deleteById(id);
                        return true;
                    } catch (IOException e) {
                        throw new FileSystemException("Error al eliminar el archivo físico", e);
                    }
                })
                .orElse(false);
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

    private ImageResponse convertToResponse(Image image) {
        return new ImageResponse(
                image.getId(),
                image.getFilename(),
                "/images/file/" + image.getFilename(),
                image.getSize(),
                image.getMimeType(),
                image.getCreatedAt()
        );
    }
}