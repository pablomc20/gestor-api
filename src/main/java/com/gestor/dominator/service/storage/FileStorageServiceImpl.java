package com.gestor.dominator.service.storage;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.gestor.dominator.components.FileStorageComponent;
import com.gestor.dominator.exceptions.custom.FileSystemException;
import com.gestor.dominator.model.client.minio.ImageRenderRs;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FileStorageServiceImpl implements FileStorageService {

    private final FileStorageComponent fileStorageComponent;

    @Override
    public String save(MultipartFile file) {
        String filename = fileStorageComponent.save(file);
        if (filename == null) {
            throw new FileSystemException("No se pudo guardar la imagen", 
                HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return filename;
    }

    @Override
    public void delete(String filename, String ext) {
        fileStorageComponent.delete(filename, ext);
    }

    @Override
    public ImageRenderRs load(String filename) {
        ImageRenderRs result = fileStorageComponent.loadFile(filename);
        if (result == null) {
            throw new FileSystemException("No existe el archivo", HttpStatus.NOT_FOUND);
        }
        if (result.contentType() == null || result.contentType().isBlank()) {
            throw new FileSystemException("Tipo de contenido no disponible", 
                HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return result;
    }
}

