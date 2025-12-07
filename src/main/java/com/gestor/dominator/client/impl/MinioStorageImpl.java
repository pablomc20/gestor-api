package com.gestor.dominator.client.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.gestor.dominator.client.MinioStorageClient;
import com.gestor.dominator.exceptions.custom.FileSystemException;
import com.gestor.dominator.model.client.minio.ImageRenderResponse;

import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import lombok.RequiredArgsConstructor;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class MinioStorageImpl implements MinioStorageClient {

    private final MinioClient minioClient;

    @Value("${minio.bucket.name}")
    private String bucketName;

    @Value("${minio.url}")
    private String endpoint;

    private static final String MESSAGE_SUCCESS = "Subido con éxito";

    @Override
    public String uploadFile(MultipartFile file, String objectKey) {
        try {

            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectKey)
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build());

            return MESSAGE_SUCCESS;
        } catch (Exception e) {
            throw new FileSystemException("Error al subir el archivo: " + e.getMessage(), e);
        }
    }

    @Override
    public String uploadFile(byte[] thumbBytes, String objectName, String contentType) {
        InputStream inputStream = new ByteArrayInputStream(thumbBytes);
        long size = thumbBytes.length;
        try {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .stream(inputStream, size, -1)
                            .contentType(contentType)
                            .build());

            return MESSAGE_SUCCESS;
        } catch (Exception e) {
            throw new FileSystemException("Error al subir el archivo: " + e.getMessage(), e);
        }
    }

    @Override
    public ImageRenderResponse downloadFile(String objectName) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            var response = minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .build());

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = response.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            String contentType = response.headers().get("content-type");
            if (contentType == null) {
                contentType = "application/octet-stream";
            }

            return new ImageRenderResponse(outputStream.toByteArray(), contentType);
        } catch (Exception e) {
            throw new FileSystemException("Error al descargar el archivo: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteFile(String objectName) {
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .build());
        } catch (Exception e) {
            throw new FileSystemException("Error al eliminar el archivo: " + e.getMessage(), e);
        }
    }
}
