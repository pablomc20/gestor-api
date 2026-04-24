package com.gestor.dominator.config;

import io.minio.MinioClient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfig {

    @Value("${minio.url}")
    private String url;

    @Value("${minio.access.key}")
    private String accessKey;

    @Value("${minio.secret.key}")
    private String secretKey;

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
}