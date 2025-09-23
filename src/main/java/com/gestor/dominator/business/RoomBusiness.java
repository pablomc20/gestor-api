package com.gestor.dominator.business;

import com.gestor.dominator.components.ObjectIdConverter;
import com.gestor.dominator.dto.room.RoomRequest;
import com.gestor.dominator.dto.room.RoomResponse;
import com.gestor.dominator.exceptions.custom.DataValidationException;
import com.gestor.dominator.model.Room;
import com.gestor.dominator.repository.RoomRepository;
import com.gestor.dominator.service.RoomService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomBusiness implements RoomService {

    private final RoomRepository roomRepository;

    private final ObjectIdConverter objectIdConverter;

    @Override
    public List<RoomResponse> getAllRooms() {
        return roomRepository.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<RoomResponse> getRoomById(String id) {
        return roomRepository.findById(objectIdConverter.stringToObjectId(id))
                .map(this::convertToResponse);
    }

    @Override
    public Optional<RoomResponse> getRoomBySlug(String slug) {
        return roomRepository.findBySlug(slug)
                .map(this::convertToResponse);
    }

    @Override
    public RoomResponse createRoom(RoomRequest request) {
        if (roomRepository.existsBySlug(request.getSlug())) {
            throw new DataValidationException("Ya existe una sala con el slug: " + request.getSlug());
        }
        if (roomRepository.existsByName(request.getName())) {
            throw new DataValidationException("Ya existe una sala con el nombre: " + request.getName());
        }
        Room room = new Room(request.getName(), request.getSlug());
        Room savedRoom = roomRepository.save(room);
        return convertToResponse(savedRoom);
    }

    @Override
    public Optional<RoomResponse> updateRoom(String id, RoomRequest request) {
        return roomRepository.findById(objectIdConverter.stringToObjectId(id))
                .map(room -> {
                    if (!room.getSlug().equals(request.getSlug()) && roomRepository.existsBySlug(request.getSlug())) {
                        throw new DataValidationException("Ya existe una sala con el slug: " + request.getSlug());
                    }
                    if (!room.getName().equals(request.getName()) && roomRepository.existsByName(request.getName())) {
                        throw new DataValidationException("Ya existe una sala con el nombre: " + request.getName());
                    }
                    room.setName(request.getName());
                    room.setSlug(request.getSlug());
                    room.setUpdatedAt(LocalDateTime.now());
                    Room updatedRoom = roomRepository.save(room);

                    return convertToResponse(updatedRoom);
                });
    }

    @Override
    public boolean deleteRoom(String id) {
        if (roomRepository.existsById(objectIdConverter.stringToObjectId(id))) {
            roomRepository.deleteById(objectIdConverter.stringToObjectId(id));
            return true;
        }
        return false;
    }

    @Override
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
