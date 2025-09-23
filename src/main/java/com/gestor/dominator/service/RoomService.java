package com.gestor.dominator.service;

import java.util.List;
import java.util.Optional;

import com.gestor.dominator.dto.room.RoomRequest;
import com.gestor.dominator.dto.room.RoomResponse;

public interface RoomService {
    List<RoomResponse> getAllRooms();

    Optional<RoomResponse> getRoomById(String id);

    Optional<RoomResponse> getRoomBySlug(String slug);

    RoomResponse createRoom(RoomRequest request);

    Optional<RoomResponse> updateRoom(String id, RoomRequest request);

    boolean deleteRoom(String id);

    List<RoomResponse> searchRoomsByName(String name);
}