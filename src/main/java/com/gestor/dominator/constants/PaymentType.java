package com.gestor.dominator.constants;

import java.util.Arrays;

public enum PaymentType {
    FIRST("FIRST"),
    SECOND("SECOND"),
    FINAL("FINAL");

    private final String value;

    PaymentType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static boolean isValid(String value) {
        return Arrays.stream(values())
                .anyMatch(t -> t.value.equals(value));
    }
}
