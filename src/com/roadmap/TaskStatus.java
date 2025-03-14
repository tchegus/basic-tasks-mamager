package com.roadmap;

import java.util.Arrays;

public enum TaskStatus {
    TODO("todo"), IN_PROGRESS("in-progress"), DONE("done");

    private final String value;

    TaskStatus(String value) {
        this.value = value;
    }

    public static TaskStatus fromValue(String value) {
        return Arrays.stream(TaskStatus.values())
                .filter(taskStatus -> taskStatus.value.equals(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid task status value: " + value));
    }
}
