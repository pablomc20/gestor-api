package com.gestor.dominator.repository;

import com.gestor.dominator.model.Image;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImageRepository extends MongoRepository<Image, String> {

    Optional<Image> findByFilename(String filename);

    List<Image> findByOriginalName(String originalName);

    boolean existsByFilename(String filename);
}