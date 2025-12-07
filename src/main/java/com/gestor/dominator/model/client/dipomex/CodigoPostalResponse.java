package com.gestor.dominator.model.client.dipomex;

import lombok.Builder;

@Builder
public record CodigoPostalResponse(
                boolean error,
                String message,
                CodigoPostal codigo_postal) {
}
