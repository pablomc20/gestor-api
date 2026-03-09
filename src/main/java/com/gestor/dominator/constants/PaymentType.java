package com.gestor.dominator.constants;

import java.util.Arrays;

public enum PaymentType {
    FIRST("FIRST", 1),
    SECOND("SECOND", 2),
    FINAL("FINAL", 3);

    private final String value;
    private final int order;

    PaymentType(String value, int order) {
        this.value = value;
        this.order = order;
    }

    public String getValue() {
        return value;
    }

    public int getOrder() {
        return order;
    }

    public static PaymentType fromValue(String value) {

        return Arrays.stream(values())
                .filter(t -> t.value.equals(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid PaymentType: " + value));
    }

}
