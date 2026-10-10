package com.gestor.dominator.constants;

public enum VisibilityType {
    PRIVATE("PRIVATE"),
    CLIENT("CLIENT"),
    PUBLIC("PUBLIC");   

    private final String value;

    VisibilityType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
