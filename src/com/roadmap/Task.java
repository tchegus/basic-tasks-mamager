package com.roadmap;


import java.io.IOException;
import java.time.LocalDateTime;

public class Task {
    private static int COUNTER = 1;
    private String id;
    private String description;
    private TaskStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Task() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        try {
            GlobalCounter.increment();
        } catch (IOException e) {
            throw new RuntimeException("Failed to increment the global counter");
        }
    }

    public Task(String id, String description, TaskStatus status) {
        this.id = id;
        this.description = description;
        this.status = status;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String formatTask() {
        return String.format("{\"id\":\"%s\",\"description\":\"%s\",\"status\":\"%s\",\"createdAt\":\"%s\",\"updatedAt\":\"%s\"}",
                id, description, status, createdAt, updatedAt);
    }

    public String formatTaskUserFriendly() {
        return String.format("Task ID: %s\nDescription: %s\nStatus: %s\nCreated At: %s\nUpdated At: %s",
                id, description, status, createdAt, updatedAt);
    }

    public String formatTaskUserFriendlyInLine() {
        return String.format("Task ID: %s, Description: %s, Status: %s, Created At: %s, Updated At: %s",
                id, description, status, createdAt, updatedAt);
    }

    public String formatTaskUserFriendlyInLine2() {
        return String.format("%s, %s,  %s,  %s, %s",
                id, description, status.name(), createdAt, updatedAt);
    }
}
