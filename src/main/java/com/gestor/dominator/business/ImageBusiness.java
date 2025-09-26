package com.gestor.dominator.business;

import com.gestor.dominator.components.FileStorageComponent;
import com.gestor.dominator.components.ObjectIdConverter;
import com.gestor.dominator.dto.image.ImageRenderResponse;
import com.gestor.dominator.dto.image.ImageResponse;
import com.gestor.dominator.dto.image.ImageUpdateRequest;
import com.gestor.dominator.exceptions.custom.DataValidationException;
import com.gestor.dominator.model.Image;
import com.gestor.dominator.repository.ImageRepository;
import com.gestor.dominator.service.ImageService;
import com.gestor.dominator.utils.FileUtils;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ImageBusiness implements ImageService {

    private final ImageRepository imageRepository;

    private final FileStorageComponent fileStorageComponent;

    private final ObjectIdConverter objectIdConverter;

    @Override
    public List<ImageResponse> getAllImages() {
        return imageRepository.findAll().stream()
                .map(image -> ImageResponse.fromModel(image))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ImageResponse> getImageById(String id) {
        return imageRepository.findById(objectIdConverter.stringToObjectId(id))
                .map(image -> ImageResponse.fromModel(image));
    }

    @Override
    public ImageResponse uploadImage(MultipartFile file) {
        String filename = fileStorageComponent.save(file);
        Image image = new Image(filename, FileUtils.getFileExtension(file.getOriginalFilename()), file.getSize(), file.getContentType());

        Image savedImage = imageRepository.save(image);

        return ImageResponse.fromModel(savedImage);
    }

    @Override
    public Optional<ImageResponse> updateImage(String id, ImageUpdateRequest request) {
        return imageRepository.findById(objectIdConverter.stringToObjectId(id))
                .map(image -> {
                    if (request.originalName() != null) {
                        image.setFilename(request.originalName());
                    }
                    Image updatedImage = imageRepository.save(image);
                    return ImageResponse.fromModel(updatedImage);
                });
    }

    @Override
    public void deleteImage(String id) {

        Image image = imageRepository.findById(objectIdConverter.stringToObjectId(id))
                .orElseThrow(() -> new DataValidationException("No existe imagen"));

        fileStorageComponent.delete(image.getFilename(), image.getExt());
        imageRepository.deleteById(objectIdConverter.stringToObjectId(id));
    }

    @Override
    public ImageRenderResponse getImageFile(String filename) {
        return fileStorageComponent.loadFile(filename);
    }

}
