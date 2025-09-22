package com.gestor.dominator.controller;

import com.gestor.dominator.dto.RoomRequest;
import com.gestor.dominator.dto.RoomResponse;
import com.gestor.dominator.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @GetMapping
    public ResponseEntity<List<RoomResponse>> getAllRooms() {
        List<RoomResponse> rooms = roomService.getAllRooms();
        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomResponse> getRoomById(@PathVariable String id) {
        Optional<RoomResponse> room = roomService.getRoomById(id);
        return room.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/slug/{slug}")
    public ResponseEntity<RoomResponse> getRoomBySlug(@PathVariable String slug) {
        Optional<RoomResponse> room = roomService.getRoomBySlug(slug);
        return room.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<List<RoomResponse>> searchRooms(@RequestParam String name) {
        List<RoomResponse> rooms = roomService.searchRoomsByName(name);
        return ResponseEntity.ok(rooms);
    }

    @PostMapping
    public ResponseEntity<RoomResponse> createRoom(@Valid @RequestBody RoomRequest request) {
        RoomResponse response = roomService.createRoom(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoomResponse> updateRoom(@PathVariable String id, @Valid @RequestBody RoomRequest request) {
        Optional<RoomResponse> updatedRoom = roomService.updateRoom(id, request);
        return updatedRoom.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRoom(@PathVariable String id) {
        boolean deleted = roomService.deleteRoom(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}