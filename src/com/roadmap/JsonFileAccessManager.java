package com.roadmap;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * This class is the gateway to the JSON file
 * <p>
 * // This class is used to read and write JSON files
 * // It is used to store the tasks in a JSON file
 * // The tasks are stored in a JSON file in the following format:
 * // [ { "id": 1, "title": "Task 1", "description": "Description 1", "status": "TODO" }, { "id": 2, "title": "Task 2", "description": "Description 2", "status": "IN_PROGRESS" } ]
 * // Or { "id": 1, "title": "Task 1", "description": "Description 1", "status": "TODO" }
 * // The JSON file is read and written using the following methods:
 * // - readTasksFromFile: Reads the tasks from the JSON file
 * // - writeTasksToFile: Writes the tasks to the JSON file
 * // - writeTaskToFile: Writes the task to the JSON file
 * // - getTask: Gets the task with the specified ID
 * // - addTask: Adds a new task
 * // - updateTask: Updates the task with the specified ID
 * // - deleteTask: Deletes the task with the specified ID
 * // The JSON file is stored in the db folder of the project
 * // The JSON file is named tasks-tracker.json
 * // The JSON file is read and written using the following classes:
 * // - Files: Reads and Writes the tasks from the JSON file
 * // The JSON file is read and written using the following libraries:
 * // - java.nio.file.Files: Reads and Writes the tasks from the JSON file
 */
public class JsonFileAccessManager {


    private static final String FILE_PATH = "tasks-tracker.json";


    public JsonFileAccessManager() throws IOException {
        createFileIfNotExists();
    }

    private void createFileIfNotExists() throws IOException {
        Path jsonFilepath = Paths.get(FILE_PATH);
        if (!Files.exists(jsonFilepath)) {
            Files.createFile(jsonFilepath);
            Files.write(jsonFilepath, "[]".getBytes()); // Initialize with an empty JSON array
        }
    }

    public void cleanTaskTracker() throws IOException {
        Path jsonFilepath = Paths.get(FILE_PATH);
        Files.write(jsonFilepath, "[]".getBytes()); // Initialize with an empty JSON array
    }

    /**
     * Reads the tasks from the JSON file
     *
     * @return the list of tasks
     * @throws IOException if an I/O error occurs
     */
    public List<Task> readTasksFromFile() throws IOException {
        List<Task> tasks = new ArrayList<>();
        List<String> lines = Files.readAllLines(Paths.get(FILE_PATH));
        for (String line : lines) {
            if (line.isBlank() || line.equals("[") || line.equals("]")) {
                continue;
            }
            tasks.add(parseTask(line));
        }
        return tasks;
    }

    /**
     * Writes the tasks to the JSON file
     *
     * @param tasks the list of tasks
     * @throws IOException if an I/O error occurs
     */
    public void writeTasksToFile(List<Task> tasks) throws IOException {
        List<String> lines = new ArrayList<>();
        lines.add("[");
        for (Task task : tasks) {
            lines.add("\t" + formatTask(task) + ",");
        }
        lines.set(lines.size() - 1, lines.getLast().replace("},", "}"));
        lines.add("]");
        Files.write(Paths.get(FILE_PATH), lines, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);
    }

    /**
     * Writes the task to the JSON file
     *
     * @param task the task
     * @throws IOException if an I/O error occurs
     */
    public void writeTaskToFile(Task task, Action action) throws IOException {

        Path path = Paths.get(FILE_PATH);
        List<String> lines = Files.readAllLines(path).stream()
                .filter(line -> !line.equals("]"))
                .filter(line -> !line.equals("["))
                .toList();
        CopyOnWriteArrayList<String> copyOnWriteArrayList = new CopyOnWriteArrayList<>(lines);
        if (action == Action.UPDATE) {
            copyOnWriteArrayList.removeIf(line -> line.contains(task.getId()));
        }
        copyOnWriteArrayList.add(formatTask(task));
        List<Task> tasks = copyOnWriteArrayList.stream()
                .filter(line -> !line.isBlank())
                .map(this::parseTask)
                .toList();
        writeTasksToFile(tasks);
    }

    /**
     * Gets the task with the specified ID
     *
     * @param taskId the ID of the task
     * @return the task with the specified ID, or null if the task is not found
     * @throws IOException if an I/O error occurs
     */
    public Task getTask(String taskId) throws IOException {
        return readTasksFromFile().stream()
                .filter(task -> task.getId().equals(taskId))
                .findFirst()
                .orElse(null);
    }

    /**
     * Adds a new task
     *
     * @param newTask the new task
     * @return the new task
     * @throws IOException if an I/O error occurs
     */
    public Task addTask(Task newTask) throws IOException {
        writeTaskToFile(newTask, Action.ADD);
        return newTask;
    }

    /**
     * Updates the task with the specified ID
     *
     * @param taskId      the ID of the task
     * @param updatedTask the updated task
     * @return the updated task
     * @throws IOException if an I/O error occurs
     */
    public Task updateTask(String taskId, Task updatedTask) throws IOException {
        final Task foundTask = getTask(taskId);
        if (foundTask != null) {
            foundTask.setDescription(updatedTask.getDescription());
            foundTask.setStatus(updatedTask.getStatus());
            foundTask.setUpdatedAt(LocalDateTime.now());
            writeTaskToFile(foundTask, Action.UPDATE);
            //Files.write(Paths.get(FILE_PATH), formatTask(foundTask).getBytes(), StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
        }
        return foundTask;
    }

    /**
     * Deletes the task with the specified ID
     *
     * @param taskId the ID of the task
     * @return true if the task is deleted, false otherwise
     * @throws IOException if an I/O error occurs
     */
    public boolean deleteTask(String taskId) throws IOException {
        List<Task> tasks = readTasksFromFile();
        boolean remove = tasks.removeIf(task -> task.getId().equals(taskId));
        writeTasksToFile(tasks);
        return remove;
    }

    private Task parseTask(String json) {
        String[] fields = json.replace("[", "").replace("]", "").replace("{", "").replace("}", "").split(",");
        Task task = new Task();
        for (String field : fields) {
            String[] keyValue = field.split(":");
            String key = keyValue[0].trim().replace("\"", "");
            String value = keyValue[1].trim().replace("\"", "");
            switch (key) {
                case "id":
                    task.setId(value);
                    break;
                case "description":
                    task.setDescription(value);
                    break;
                case "status":
                    task.setStatus(TaskStatus.valueOf(value));
                    break;
                case "createdAt":
                    String createdAt = keyValue[1].trim().replace("\"", "") + ":" + keyValue[2].trim().replace("\"", "") + ":" + keyValue[3].trim().replace("\"", "");
                    task.setCreatedAt(LocalDateTime.parse(createdAt));
                    break;
                case "updatedAt":
                    String updatedAt = keyValue[1].trim().replace("\"", "") + ":" + keyValue[2].trim().replace("\"", "") + ":" + keyValue[3].trim().replace("\"", "");
                    task.setUpdatedAt(LocalDateTime.parse(updatedAt));
                    break;
            }
        }
        return task;
    }

    private String formatTask(Task task) {
        return String.format("{\"id\":\"%s\",\"description\":\"%s\",\"status\":\"%s\",\"createdAt\":\"%s\",\"updatedAt\":\"%s\"}",
                task.getId(), task.getDescription(), task.getStatus(), task.getCreatedAt(), task.getUpdatedAt());
    }
}