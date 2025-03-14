import com.roadmap.GlobalCounter;
import com.roadmap.JsonFileAccessManager;
import com.roadmap.Task;
import com.roadmap.TaskManagerImpl;
import com.roadmap.TaskStatus;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Main class for the Task Manager CLI application.
 */
public class Main {

    public static void main(String[] args) {

        String command = "";
        String argument = "";
        String newDescription = "";

        //Manage the list command
        if (args.length == 1) {
            if (!args[0].equals("list")) {
                System.out.println("Usage: task-cli <command> <arguments>");
                System.exit(1);
            }
            command = args[0];
        }

        //Manage add, delete, mark-in-progress, mark-done
        // list done, list todo, list in-in-progress commands
        if (args.length == 2) {
            command = args[0];
            argument = args[1];
        }
        //Manage update command
        if (args.length > 2) {
            command = args[0];
            argument = args[1];
            newDescription = args[2];
        }

        try {
            JsonFileAccessManager jsonFileAccessManager = new JsonFileAccessManager();
            TaskManagerImpl taskManager = new TaskManagerImpl(jsonFileAccessManager);

            switch (command) {
                case "add":
                    Task newTask = new Task();
                    String id = String.valueOf(GlobalCounter.getCounter());
                    newTask.setId(id);
                    newTask.setDescription(argument);
                    newTask.setStatus(TaskStatus.TODO);
                    newTask.setCreatedAt(LocalDateTime.now());
                    newTask.setUpdatedAt(LocalDateTime.now());
                    taskManager.createTask(newTask);
                    GlobalCounter.updateGlobalCounterSequence(Integer.parseInt(id));
                    break;
                case "update":
                    Task updatedTask = taskManager.getTask(argument);
                    updatedTask.setDescription(newDescription);
                    updatedTask.setUpdatedAt(LocalDateTime.now());
                    taskManager.updateTask(argument, updatedTask);
                    break;
                case "delete":
                    taskManager.deleteTask(argument);
                    System.out.println("Task deleted: " + argument);
                    break;
                case "mark-in-progress":
                    Task inProgressTask = taskManager.getTask(argument);
                    inProgressTask.setStatus(TaskStatus.IN_PROGRESS);
                    inProgressTask.setUpdatedAt(LocalDateTime.now());
                    taskManager.updateTask(argument, inProgressTask);
                    break;
                case "mark-done":
                    Task doneTask = taskManager.getTask(argument);
                    doneTask.setStatus(TaskStatus.DONE);
                    doneTask.setUpdatedAt(LocalDateTime.now());
                    taskManager.updateTask(argument, doneTask);
                    break;
                case "list":
                    if (argument == null || argument.isEmpty()) {
                        List<Task> tasks = taskManager.getAllTasks();
                        tasks.forEach(task -> System.out.println(task.formatTaskUserFriendlyInLine2()));
                    } else {
                        TaskStatus status = TaskStatus.fromValue(argument);
                        List<Task> tasks = taskManager.getTasksByStatus(status);
                        tasks.forEach(task -> System.out.println(task.formatTaskUserFriendlyInLine2()));
                    }
                    break;
                // Add more cases for other commands like update, delete, etc.
                default:
                    System.out.println("Unknown command: " + command);
                    System.out.println("Usage: task-cli <command> <arguments>");
                    System.out.println("Refer to the README.md file for more information.");
                    System.exit(1);
            }
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
            System.exit(1);
        }
    }

    private static void initApp(JsonFileAccessManager jsonFileAccessManager) throws IOException {
        GlobalCounter.resetCounter();
        jsonFileAccessManager.cleanTaskTracker();
    }

    private static Task createTask(String description, TaskStatus status) throws IOException {
        Task newTask = new Task();
        newTask.setId(String.valueOf(GlobalCounter.getCounter()));
        newTask.setDescription(description);
        newTask.setStatus(status == null ? TaskStatus.TODO : status);
        return newTask;
    }
}