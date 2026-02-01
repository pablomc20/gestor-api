package com.gestor.dominator.business;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.gestor.dominator.client.DipomexClient;
import com.gestor.dominator.client.DipomexProperties;
import com.gestor.dominator.dto.address.AdressCPResult;
import com.gestor.dominator.dto.address.StatesResult;
import com.gestor.dominator.model.client.dipomex.CodigoPostalResponse;
import com.gestor.dominator.model.client.dipomex.EstadoResponse;
import com.gestor.dominator.service.address.AddressService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AddressBusiness implements AddressService {

   private final DipomexClient dipomexClient;
   private final DipomexProperties dipomexProperties;

   @Override
   public AdressCPResult buscarCP(String cp) {
      CodigoPostalResponse response = dipomexClient.buscarCP(cp, dipomexProperties.getToken());

      return AdressCPResult.builder()
            .cp(response.codigo_postal().codigo_postal())
            .colonias(response.codigo_postal().colonias())
            .municipio(response.codigo_postal().municipio())
            .estado(response.codigo_postal().estado())
            .build();
   }

   @Override
   public StatesResult buscarEstados() {
      EstadoResponse estados = dipomexClient.buscarEstados(dipomexProperties.getToken());

      List<String> estadosList = new ArrayList<>();
      estados.estados().forEach(e -> {
         estadosList.add(e.nombre());
      });

      return StatesResult.builder()
            .estados(estadosList)
            .build();
   }
}
