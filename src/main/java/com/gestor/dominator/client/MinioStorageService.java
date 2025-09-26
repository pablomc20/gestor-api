package com.gestor.dominator.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.gestor.dominator.exceptions.custom.FileSystemException;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class MinioStorageService {

    private final MinioClient minioClient;

    @Autowired
    public MinioStorageService(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    @Value("${minio.bucket.name}")
    private String bucketName;

    @Value("${minio.url}")
    private String endpoint;

     public String uploadFile(MultipartFile file) {
        try {
            // Generar un nombre de archivo único
            String fileName = System.currentTimeMillis() + "-" + file.getOriginalFilename();

            minioClient.putObject(
                PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(fileName)
                    .stream(file.getInputStream(), file.getSize(), -1)
                    .contentType(file.getContentType())
                    .build()
            );

            // Devuelve la URL pública del archivo
            return endpoint + bucketName + "/" + fileName;
        } catch (Exception e) {
            throw new FileSystemException("Error al subir el archivo: " + e.getMessage(), e);
        }
    }
}
