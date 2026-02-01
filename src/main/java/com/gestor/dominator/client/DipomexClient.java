package com.gestor.dominator.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.gestor.dominator.model.client.dipomex.CodigoPostalResponse;
import com.gestor.dominator.model.client.dipomex.EstadoResponse;

@FeignClient(name = "dipomexClient", url = "${client.dipomex.url}${client.dipomex.version}")
public interface DipomexClient {

    @GetMapping("/codigo_postal")
    CodigoPostalResponse buscarCP(
            @RequestParam("cp") String cp,
            @RequestHeader("APIKEY") String token);

    @GetMapping("/estados")
    EstadoResponse buscarEstados(
            @RequestHeader("APIKEY") String token);
}
