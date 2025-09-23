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
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ImageResponse> getImageById(String id) {
        return imageRepository.findById(objectIdConverter.stringToObjectId(id))
                .map(this::convertToResponse);
    }

    @Override
    public ImageResponse uploadImage(MultipartFile file) {
        String filename = fileStorageComponent.save(file);
        Image image = new Image(filename, file.getOriginalFilename(), file.getSize(), file.getContentType());

        Image savedImage = imageRepository.save(image);

        return convertToResponse(savedImage);
    }

    @Override
    public Optional<ImageResponse> updateImage(String id, ImageUpdateRequest request) {
        return imageRepository.findById(objectIdConverter.stringToObjectId(id))
                .map(image -> {
                    if (request.originalName() != null) {
                        image.setOriginalName(request.originalName());
                    }
                    Image updatedImage = imageRepository.save(image);
                    return convertToResponse(updatedImage);
                });
    }

    @Override
    public void deleteImage(String id) {

        Image image = imageRepository.findById(objectIdConverter.stringToObjectId(id))
                .orElseThrow(() -> new DataValidationException("No existe imagen"));

        fileStorageComponent.delete(image.getFilename());
        imageRepository.deleteById(objectIdConverter.stringToObjectId(id));
    }

    @Override
    public ImageRenderResponse getImageFile(String filename) {
        return fileStorageComponent.loadFile(filename);
    }

    private ImageResponse convertToResponse(Image image) {
        return new ImageResponse(
                image.getId(),
                image.getFilename(),
                "/images/file/" + image.getFilename(),
                image.getSize(),
                image.getMimeType(),
                image.getCreatedAt());
    }
}
