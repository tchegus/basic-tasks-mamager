# basic-tasks-manager
Basic-tasks-manager is task tracker project used to track and manage your tasks.

# Requirements
Java 21

# Project url
https://roadmap.sh/projects/task-tracker

# Features
Add a Task: Add a new task with a description.
Update a Task: Update the description of an existing task.
Delete a Task: Remove a task by its ID.
Mark a Task: Mark a task as "in progress" or "done."
List Tasks: List all tasks or filter them by status (e.g., todo, in progress, done).

# Installation
Clone the repository:
git clone https://github.com/tchegus/basic-tasks-manager.git

# Build Compile & run :

- Compile the source code and run the app on Windows:
  use task-cli-compile.bat and task-cli.bat from a terminal windows or from your IDE
  .\task-cli-compile.bat
  .\task-cli.bat [command] [arguments]

-  Compile the source code and run the app on others OS:
   use task-cli-compile.sh and task-cli.sh from a terminal (Git Bash on windows)
  ./task-cli-compile.sh
  ./task-cli.sh [command] [arguments]

# Usage
 
##Adding a new task

.\task-cli.bat add "Buy groceries"
- Task added: 3, Go to the park with my loves one,  TODO,  2025-03-14T09:54:24.246790200, 2025-03-14T09:54:24.246790200

## Updating a task

.\task-cli.bat update 1 "Buy groceries and cook dinner"
- Task updated: 1, Buy groceries and cook dinner,  TODO,  2025-03-13T23:50:14.255412800, 2025-03-14T09:56:50.289910600 to status: TODO

## Deleting a task

.\task-cli.bat delete 1
- Task with id: 1 was deleted
- Task deleted: 1

## Marking a task as in progress

.\task-cli.bat mark-in-progress 1

Marking a task as done
.\task-cli.bat mark-done 1

## Listing all tasks

.\task-cli.bat list
- 33, Buy groceries and cook dinner,  TODO,  2025-03-13T22:09:37.508735, 2025-03-13T22:10:01.154690300
- 32, Buy groceries,  DONE,  2025-03-13T22:09:32.844522900, 2025-03-13T22:39:05.649898900
- 31, Buy groceries and cook dinner,  IN_PROGRESS,  2025-03-13T22:09:30.296383900, 2025-03-14T10:25:35.801111800

## Listing tasks by status

.\task-cli.bat list todo
- 33, Buy groceries and cook dinner,  TODO,  2025-03-13T22:09:37.508735, 2025-03-13T22:10:01.154690300

.\task-cli.bat list in-progress
- 31, Buy groceries and cook dinner,  IN_PROGRESS,  2025-03-13T22:09:30.296383900, 2025-03-14T10:25:35.801111800

.\task-cli.bat list done
- 32, Buy groceries,  DONE,  2025-03-13T22:09:32.844522900, 2025-03-13T22:39:05.649898900
