package com.gestor.dominator.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gestor.dominator.business.AddressBusiness;
import com.gestor.dominator.dto.address.AdressCPResult;
import com.gestor.dominator.dto.address.StatesResult;


@RestController
@RequestMapping("/address")
public class AddressController {

  private final AddressBusiness addressBusiness;

  public AddressController(AddressBusiness addressBusiness) {
    this.addressBusiness = addressBusiness;
  }

  @GetMapping("/cp/{cp}")
  public AdressCPResult buscarCP(@PathVariable String cp) {
    return addressBusiness.buscarCP(cp);
  }

  @GetMapping("/estados")
  public StatesResult buscarEstados() {
    return addressBusiness.buscarEstados();
  }

}
