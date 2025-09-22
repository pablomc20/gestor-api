package com.gestor.dominator.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.gestor.dominator.dto.RoomResponse;
import com.gestor.dominator.service.RoomService;

@RestController
@RequestMapping(path="/public")
public class PublicController {

    @Autowired
    private RoomService roomService;

    @GetMapping(value = "/rooms")
    public ResponseEntity<List<RoomResponse>> getAllRooms() {
        List<RoomResponse> rooms = roomService.getAllRooms();
        return ResponseEntity.ok(rooms);
    }

    @GetMapping(path="/health")
    public @ResponseBody String getHealthStatus() {
        return "Servicio Dominator está funcionando correctamente.";
    }

    @GetMapping(path="/version")
    public @ResponseBody String getVersion() {
        return "Dominator API v1.0.0";
    }

}