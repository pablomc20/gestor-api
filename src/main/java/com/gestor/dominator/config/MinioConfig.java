package com.gestor.dominator.config;

import io.minio.MinioClient;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
public class MinioConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(MinioConfig.class);

    @Value("${minio.url}")
    private String url;

    @Value("${minio.access.key}")
    private String accessKey;

    @Value("${minio.secret.key}")
    private String secretKey;

    @Value("${minio.bucket.name}")
    private String bucketName;

    @Bean
    public MinioClient minioClient() {
        // Log de seguridad para verificar longitudes y descartar truncamiento por caracteres como #
        System.out.println("Configurando MinioClient:");
        System.out.println("- Endpoint: " + url.trim());
        System.out.println("- AccessKey length: " + (accessKey != null ? accessKey.trim().length() : 0));
        System.out.println("- SecretKey length: " + (secretKey != null ? secretKey.trim().length() : 0));

        return MinioClient.builder()
                .endpoint(url.trim())
                .credentials(accessKey.trim(), secretKey.trim())
                .region("us-east-1")
                .build();
    }

    @Bean
    public ApplicationRunner initializeMinioBucket(MinioClient minioClient) {
        return (ApplicationArguments args) -> {
            boolean bucketExists = minioClient.bucketExists(
                    BucketExistsArgs.builder().bucket(bucketName).build());

            if (!bucketExists) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
                LOGGER.info("Bucket de MinIO creado: {}", bucketName);
            } else {
                LOGGER.info("El bucket de MinIO ya existe: {}", bucketName);
            }
        };
    }
}