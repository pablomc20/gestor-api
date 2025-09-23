
package com.gestor.dominator.service;

import com.gestor.dominator.dto.image.ImageRenderResponse;
import com.gestor.dominator.dto.image.ImageResponse;
import com.gestor.dominator.dto.image.ImageUpdateRequest;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface ImageService {
    List<ImageResponse> getAllImages();

    Optional<ImageResponse> getImageById(String id);

    ImageResponse uploadImage(MultipartFile file);

    Optional<ImageResponse> updateImage(String id, ImageUpdateRequest request);

    void deleteImage(String id);

    ImageRenderResponse getImageFile(String fileName);
}
