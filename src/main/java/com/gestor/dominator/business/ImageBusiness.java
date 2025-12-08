package com.gestor.dominator.business;

import com.gestor.dominator.components.FileStorageComponent;
import com.gestor.dominator.dto.image.ImageCreateResult;
import com.gestor.dominator.dto.image.ImageRenderResult;
import com.gestor.dominator.dto.image.ImageResult;
import com.gestor.dominator.exceptions.custom.DataValidationException;
import com.gestor.dominator.exceptions.custom.FileSystemException;
import com.gestor.dominator.exceptions.custom.PostgreDbException;
import com.gestor.dominator.model.client.minio.ImageRenderRs;
import com.gestor.dominator.model.postgre.image.ImageCreateRs;
import com.gestor.dominator.model.postgre.image.ImageRq;
import com.gestor.dominator.model.postgre.image.ImageRs;
import com.gestor.dominator.repository.ImageRepository;
import com.gestor.dominator.service.ImageService;
import com.gestor.dominator.utils.FileUtils;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImageBusiness implements ImageService {

    private final ImageRepository imageRepository;

    private final FileStorageComponent fileStorageComponent;

    @Override
    public ImageResult getImageById(String id) {

        ImageRs image = imageRepository.findById(id);

        return ImageResult.fromModel(image);
    }

    @Override
    public ImageCreateResult uploadImage(MultipartFile file) {
        // Guardar la imagen en MINIO
        String filename = fileStorageComponent.save(file);

        if (filename == null) {
            throw new FileSystemException("No se pudo guardar la imagen", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // Guardar la imagen en la BD
        String ext = FileUtils.getFileExtension(file.getOriginalFilename());
        ImageRq image = ImageRq.builder()
                .filename(filename)
                .ext(ext)
                .size(file.getSize())
                .mimeType(file.getContentType())
                .build();

        ImageCreateRs savedImage = imageRepository.save(image);

        if (!savedImage.status().equals("ok")) {
            throw new PostgreDbException("Error al crear imagen");
        }

        return ImageCreateResult.builder()
                .idImage(savedImage.idImage())
                .build();
    }

    @Override
    public void deleteImage(String id) {

        ImageRs image = imageRepository.findById(id);

        if (image == null) {
            throw new DataValidationException("No existe imagen");
        }

        fileStorageComponent.delete(image.filename(), image.ext());
        imageRepository.deleteById(id);
    }

    @Override
    public ImageRenderResult getImageFile(String filename) {
        ImageRenderRs imageRenderRs = fileStorageComponent.loadFile(filename);

        if (imageRenderRs == null) {
            throw new PostgreDbException("No existe imagen");
        }

        return ImageRenderResult.builder()
                .imageData(imageRenderRs.imageData())
                .mediaType(MediaType.parseMediaType(imageRenderRs.contentType()))
                .build();
    }

}
