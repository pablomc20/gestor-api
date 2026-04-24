package com.gestor.dominator.constants;

import java.util.Arrays;

public enum ProjectUserType {

    NEW("new"),
    IN_COURSE("in_course"),
    FINISHED("finished");

    private final String value;

    ProjectUserType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static ProjectUserType fromValue(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Project user type is required");
        }

        return Arrays.stream(values())
                .filter(status -> status.value.equalsIgnoreCase(value) || status.name().equalsIgnoreCase(value.replaceAll("[- ]", "_")))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid project user type: " + value));
    }

    @Override
    public String toString() {
        return value;
    }
}
