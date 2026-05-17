package com.gestor.dominator.constants;

public enum UserType {
    EMPLOYEE("employee"),
    CLIENT("client");

    private final String value;

    UserType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
