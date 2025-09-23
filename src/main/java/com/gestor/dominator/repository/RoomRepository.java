package com.gestor.dominator.repository;

import com.gestor.dominator.model.Room;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends MongoRepository<Room, ObjectId> {

    Optional<Room> findBySlug(String slug);

    Optional<Room> findByName(String name);

    List<Room> findByNameContainingIgnoreCase(String name);

    boolean existsBySlug(String slug);

    boolean existsByName(String name);
}