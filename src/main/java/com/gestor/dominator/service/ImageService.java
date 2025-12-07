
package com.gestor.dominator.service;

import org.springframework.web.multipart.MultipartFile;

import com.gestor.dominator.model.client.minio.ImageRenderResponse;
import com.gestor.dominator.model.client.minio.ImageResponse;
import com.gestor.dominator.model.client.minio.ImageUpdateRequest;

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
