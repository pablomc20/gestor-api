package com.gestor.dominator.client;

import org.springframework.web.multipart.MultipartFile;

import com.gestor.dominator.model.client.minio.ImageRenderRs;

public interface MinioStorageClient {
  String uploadFile(MultipartFile file, String objectKey);

  String uploadFile(byte[] thumbBytes, String objectName, String contentType);

  ImageRenderRs downloadFile(String objectName);

  void deleteFile(String objectKey);
}
