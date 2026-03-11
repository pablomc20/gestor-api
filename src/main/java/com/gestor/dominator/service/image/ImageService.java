
package com.gestor.dominator.service.image;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.gestor.dominator.dto.image.ImageCreateResult;
import com.gestor.dominator.dto.image.ImageRenderResult;
import com.gestor.dominator.dto.image.ImageResult;

public interface ImageService {

    ImageResult getImageById(String id);

    ImageCreateResult uploadImage(List<MultipartFile> file);

    void deleteImage(String id);

    ImageRenderResult getImageFile(String fileName);
}
