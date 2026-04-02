package com.gestor.dominator.constants;

import java.util.Arrays;

public enum StatusProject {
    NOT_APPLIED("NOT_APPLIED", "DESIGNED", "Se ha iniciado el proceso de diseño del proyecto", ""),
    DESIGNED("DESIGNED", "ACQUIRED", "Estamos en proceso de adquisición de materiales para el proyecto", ""),
    ACQUIRED("ACQUIRED", "CUT", "Se estan realizando todos los cortes de madera", ""),
    CUT("CUT", "ASSEMBLED", "El proyecto está en proceso de ensamblaje", ""),
    ASSEMBLED("ASSEMBLED", "FINISHED", "El proyecto ha sido ensamblado", "PROGRESS"),
    FINISHED("FINISHED", "DELIVERED", "El mueble esta listo para ser entregado / instalado", "FINAL"),
    DELIVERED("DELIVERED", "", "Tu mueble ha sido entregado / instalado", ""),;

    public final String value;
    public final String nextStatus;
    public final String statusMessage;
    public final String imageType;

    StatusProject(String value, String nextStatus, String statusMessage, String imageType) {
        this.value = value;
        this.nextStatus = nextStatus;
        this.statusMessage = statusMessage;
        this.imageType = imageType;
    }

    public static String getStatusMessage(String value) {
        return Arrays.stream(values())
                .filter(t -> t.value.equals(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid StatusProject: " + value)).statusMessage;
    }

    public static String retrieveNextStatusProject(String value) {
        StatusProject statusFiltered = Arrays.stream(values())
                .filter(t -> t.value.equals(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid StatusProject: " + value));

        return statusFiltered.nextStatus;
    }

    public static StatusProject fromValue(String value) {
        return Arrays.stream(values())
                .filter(t -> t.value.equals(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid Status: " + value));
    }

}
