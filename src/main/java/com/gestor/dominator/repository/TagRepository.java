package com.gestor.dominator.repository;

import com.gestor.dominator.model.Tag;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends MongoRepository<Tag, String> {

    Optional<Tag> findBySlug(String slug);

    Optional<Tag> findByName(String name);

    List<Tag> findByNameContainingIgnoreCase(String name);

    List<Tag> findByType(String type);

    List<Tag> findByGroup(String groupId);
}