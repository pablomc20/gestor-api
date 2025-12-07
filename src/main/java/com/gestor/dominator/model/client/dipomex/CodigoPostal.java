package com.gestor.dominator.model.client.dipomex;

import java.util.List;

public record CodigoPostal(
                String estado,
                String estado_abreviatura,
                String municipio,
                String centro_reparto,
                String codigo_postal,
                List<String> colonias) {
}
