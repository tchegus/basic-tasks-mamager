package com.roadmap;

import java.util.List;

public interface TaskManager {

    Task getTask(String taskId);

    Task createTask(Task task);

    void createTasks(List<Task> tasks);

    Task updateTask(String taskId, Task task);

    void deleteTask(String taskId);

    List<Task> getAllTasks();

    List<Task> getTasksByStatus(TaskStatus status);
}
