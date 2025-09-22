package com.gestor.dominator.service;

import com.gestor.dominator.dto.RoomRequest;
import com.gestor.dominator.dto.RoomResponse;
import com.gestor.dominator.model.Room;
import com.gestor.dominator.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    public List<RoomResponse> getAllRooms() {
        return roomRepository.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public Optional<RoomResponse> getRoomById(String id) {
        return roomRepository.findById(id)
                .map(this::convertToResponse);
    }

    public Optional<RoomResponse> getRoomBySlug(String slug) {
        return roomRepository.findBySlug(slug)
                .map(this::convertToResponse);
    }

    public RoomResponse createRoom(RoomRequest request) {
        // Validar que el slug no exista
        if (roomRepository.existsBySlug(request.getSlug())) {
            throw new IllegalArgumentException("Ya existe una sala con el slug: " + request.getSlug());
        }

        // Validar que el nombre no exista
        if (roomRepository.existsByName(request.getName())) {
            throw new IllegalArgumentException("Ya existe una sala con el nombre: " + request.getName());
        }

        Room room = new Room(request.getName(), request.getSlug());
        Room savedRoom = roomRepository.save(room);
        return convertToResponse(savedRoom);
    }

    public Optional<RoomResponse> updateRoom(String id, RoomRequest request) {
        return roomRepository.findById(id)
                .map(room -> {
                    // Validar slug único (excluyendo el actual)
                    if (!room.getSlug().equals(request.getSlug()) && roomRepository.existsBySlug(request.getSlug())) {
                        throw new IllegalArgumentException("Ya existe una sala con el slug: " + request.getSlug());
                    }

                    // Validar nombre único (excluyendo el actual)
                    if (!room.getName().equals(request.getName()) && roomRepository.existsByName(request.getName())) {
                        throw new IllegalArgumentException("Ya existe una sala con el nombre: " + request.getName());
                    }

                    room.setName(request.getName());
                    room.setSlug(request.getSlug());
                    room.setUpdatedAt(LocalDateTime.now());
                    Room updatedRoom = roomRepository.save(room);
                    return convertToResponse(updatedRoom);
                });
    }

    public boolean deleteRoom(String id) {
        if (roomRepository.existsById(id)) {
            roomRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<RoomResponse> searchRoomsByName(String name) {
        return roomRepository.findByNameContainingIgnoreCase(name).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    private RoomResponse convertToResponse(Room room) {
        return new RoomResponse(
                room.getId(),
                room.getName(),
                room.getSlug(),
                room.getVersion(),
                room.getCreatedAt(),
                room.getUpdatedAt()
        );
    }
}