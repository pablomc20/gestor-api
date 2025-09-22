package com.gestor.dominator.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/public")
public class PublicController {

    @GetMapping(path="/info")
    public @ResponseBody String getPublicInfo() {
        return "Esta es información pública accesible sin autenticación.";
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