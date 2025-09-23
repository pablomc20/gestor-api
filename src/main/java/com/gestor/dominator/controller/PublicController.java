package com.gestor.dominator.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.gestor.dominator.components.ObjectIdConverter;
import com.gestor.dominator.dto.room.RoomResponse;
import com.gestor.dominator.module.Author;
import com.gestor.dominator.module.AuthorRepository;
import com.gestor.dominator.module.Profile;
import com.gestor.dominator.module.ProfileRepository;
import com.gestor.dominator.service.RoomService;

@RestController
@RequestMapping(path="/public")
public class PublicController {

    @Autowired
    private RoomService roomService;

    @Autowired
    private AuthorRepository userRepository;
    
    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private ObjectIdConverter objectIdConverter;


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
        userRepository.save(Author.builder().username("root").password("1234")
        .profileId(objectIdConverter.stringToObjectId("68d1de1e27b1a314da732781")).build());

        System.out.println(userRepository.findAllWithProfiles());

        // Crear nuevos perfiles de la coleccion de mongo

        // profileRepository.save(Profile.builder().website("google").bio("2genial").build());

        return "creado";
    }

}