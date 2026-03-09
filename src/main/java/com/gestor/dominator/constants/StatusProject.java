package com.gestor.dominator.constants;

import java.util.Arrays;

public enum StatusProject {
    NOT_APPLIED("NOT_APPLIED", "DESIGNED", "Se ha iniciado el proceso de diseño del proyecto"),
    DESIGNED("DESIGNED", "ACQUIRED", "Estamos en proceso de adquisición de materiales para el proyecto"),
    ACQUIRED("ACQUIRED", "CUT", "Se estan realizando todos los cortes de madera"),
    CUT("CUT", "ASSEMBLED", "El proyecto está en proceso de ensamblaje"),
    ASSEMBLED("ASSEMBLED", "FINISHED", "El proyecto ha sido ensamblado"),
    FINISHED("FINISHED", "DELIVERED", "El proyecto ha sido entregado / instalado"),;

    private final String value;
    private final String nextStatus;
    private final String statusMessage;

    StatusProject(String value, String nextStatus, String statusMessage) {
        this.value = value;
        this.nextStatus = nextStatus;
        this.statusMessage = statusMessage;
    }

    public static String getStatusMessage(String value) {
        return Arrays.stream(values())
                .filter(t -> t.value.equals(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid StatusProject: " + value))
                .statusMessage;
    }

    public static String retrieveNextStatusProject(String value) {
        StatusProject statusFiltered = Arrays.stream(values())
                .filter(t -> t.value.equals(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid StatusProject: " + value));

        return statusFiltered.nextStatus;
    }
}
