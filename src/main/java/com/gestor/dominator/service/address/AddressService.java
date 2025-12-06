package com.gestor.dominator.service.address;

import com.gestor.dominator.dto.address.AdressCPResult;
import com.gestor.dominator.dto.address.StatesResult;

public interface AddressService {
  AdressCPResult buscarCP(String cp);

  StatesResult buscarEstados();
}
