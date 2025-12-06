package com.gestor.dominator.dto.address;

import java.util.List;

import lombok.Builder;

@Builder
public record AdressCPResult(
        String cp,
        String municipio,
        String estado,
        List<String> colonias) {

}
