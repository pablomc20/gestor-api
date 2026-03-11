package com.gestor.dominator.service.storage;

import org.springframework.web.multipart.MultipartFile;

import com.gestor.dominator.model.client.minio.ImageRenderRs;

public interface FileStorageService {

    String save(MultipartFile file);

    void delete(String filename, String ext);

    ImageRenderRs load(String filename);
}

