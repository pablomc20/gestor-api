package com.gestor.dominator.business.image;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.gestor.dominator.dto.image.ImageCreateResult;
import com.gestor.dominator.dto.image.ImageRenderResult;
import com.gestor.dominator.dto.image.ImageResult;
import com.gestor.dominator.exceptions.custom.DataValidationException;
import com.gestor.dominator.exceptions.custom.PostgreDbException;
import com.gestor.dominator.model.client.minio.ImageRenderRs;
import com.gestor.dominator.model.postgre.image.ImageCreateRs;
import com.gestor.dominator.model.postgre.image.ImageRq;
import com.gestor.dominator.model.postgre.image.ImageRs;
import com.gestor.dominator.service.image.ImageDbService;
import com.gestor.dominator.service.image.ImageService;
import com.gestor.dominator.service.storage.FileStorageService;
import com.gestor.dominator.utils.FileUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImageUseCase implements ImageService {

    private final ImageDbService imageDbService;
    private final FileStorageService fileStorageService;

    @Override
    public ImageResult getImageById(String id) {
        ImageRs image = imageDbService.findById(id);
        return ImageResult.fromModel(image);
    }

    @Override
    public ImageCreateResult uploadImage(List<MultipartFile> files) {
        List<String> idImages = new ArrayList<>();

        for (MultipartFile file : files) {
            String filename = fileStorageService.save(file);

            String ext = FileUtils.getFileExtension(file.getOriginalFilename());
            ImageRq imageRq = ImageRq.builder()
                    .filename(filename)
                    .ext(ext)
                    .size(file.getSize())
                    .mimeType(file.getContentType())
                    .build();

            ImageCreateRs savedImage = imageDbService.save(imageRq);

            if (savedImage == null || !"ok".equals(savedImage.status())) {
                throw new PostgreDbException("Error al crear imagen");
            }

            idImages.add(savedImage.idImage());
        }

        return ImageCreateResult.builder()
                .idImages(idImages.toArray(new String[0]))
                .build();
    }

    @Override
    public void deleteImage(String id) {
        ImageRs image = imageDbService.findById(id);

        if (image == null) {
            throw new DataValidationException("No existe imagen");
        }

        fileStorageService.delete(image.filename(), image.ext());
        imageDbService.deleteById(id);
    }

    @Override
    public ImageRenderResult getImageFile(String filename) {
        ImageRenderRs imageRenderRs = fileStorageService.load(filename);

        String contentType = Objects.requireNonNull(imageRenderRs.contentType());
        return ImageRenderResult.builder()
                .imageData(imageRenderRs.imageData())
                .mediaType(MediaType.parseMediaType(contentType))
                .build();
    }
}

