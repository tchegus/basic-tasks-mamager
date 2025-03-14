package com.roadmap;

import java.io.IOException;
import java.util.List;

public class TaskManagerImpl implements TaskManager {

    private final JsonFileAccessManager jsonFileAccessManager;

    public TaskManagerImpl(final JsonFileAccessManager jsonFileAccessManager) {
        this.jsonFileAccessManager = jsonFileAccessManager;
    }

    public Task getTask(String taskId) {
        Task task;
        try {
            task = jsonFileAccessManager.getTask(taskId);
        } catch (IOException e) {
            throw new UnExpectedErrorException(e, "An error occurred while getting the task with id: " + taskId);
        }
        if (task == null) {
            throw new TaskNotFoundException("The task with id: " + taskId + " was not found");
        }
        return task;
    }


    public Task createTask(Task task) {
        try {
            Task newTask = jsonFileAccessManager.addTask(task);
            System.out.println("Task added: " + newTask.formatTaskUserFriendlyInLine2());
            return newTask;
        } catch (IOException e) {
            throw new UnExpectedErrorException("An error occurred while creating the task");
        }

    }

    @Override
    public void createTasks(List<Task> tasks) {
        try {
            jsonFileAccessManager.writeTasksToFile(tasks);
        } catch (IOException e) {
            throw new UnsupportedOperationException("An error occurred while creating the tasks");
        }
    }


    public Task updateTask(String taskId, Task task) {
        Task createdTask;
        try {
            createdTask = jsonFileAccessManager.updateTask(taskId, task);
        } catch (IOException e) {
            throw new UnExpectedErrorException(e, "An error occurred while updating the task with id: " + taskId);
        }
        if (createdTask == null) {
            throw new TaskNotFoundException("The task with id: " + taskId + " was not found");
        }
        System.out.println("Task updated: " + createdTask.formatTaskUserFriendlyInLine2() + " to status: " + createdTask.getStatus().name());
        return createdTask;
    }


    public void deleteTask(String taskId) {
        boolean isDeleted;
        try {
            isDeleted = jsonFileAccessManager.deleteTask(taskId);
            System.out.println("Task with id: " + taskId + " was deleted");
        } catch (IOException e) {
            throw new UnExpectedErrorException(e, "An error occurred while deleting the task with id: " + taskId);
        }
        if (!isDeleted) {
            throw new TaskNotFoundException("The task with id: " + taskId + " was not found");
        }
    }


    public List<Task> getAllTasks() {
        try {
            return jsonFileAccessManager.readTasksFromFile();
        } catch (IOException e) {
            throw new UnExpectedErrorException(e, "An error occurred while getting all tasks");
        }
    }


    public List<Task> getTasksByStatus(TaskStatus status) {
        return getAllTasks().stream()
                .filter(task -> task.getStatus().equals(status))
                .toList();
    }

    public static class UnExpectedErrorException extends RuntimeException {

        private final String message;

        public UnExpectedErrorException(String message) {
            this.message = message;
        }

        public UnExpectedErrorException(String message, Throwable cause, String message1) {
            super(message, cause);
            this.message = message1;
        }

        public UnExpectedErrorException(Throwable cause, String message) {
            this.message = message;
        }
    }

    public static class TaskNotFoundException extends RuntimeException {
        private final String message;

        public TaskNotFoundException(String message) {
            this.message = message;
        }

        public TaskNotFoundException(String message, Throwable cause, String message1) {
            super(message, cause);
            this.message = message;
        }

        public TaskNotFoundException(Throwable cause, String message) {
            this.message = message;
        }
    }

}
