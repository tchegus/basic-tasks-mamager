# basic-tasks-manager
Basic-tasks-manager is task tracker project used to track and manage your tasks

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

- Compile the source code :
  Run task-cli-compile.bat from a terminal windows or from your IDE (.\task-cli-compile.bat )

- Run the application:
Run task-cli [command] [arguments]

# Usage
 
Adding a new task
.\task-cli.bat add "Buy groceries"
Output: 

Updating a task
.\task-cli.bat update 1 "Buy groceries and cook dinner"
Output: 

Deleting a task
.\task-cli.bat delete 1
Output:

Marking a task as in progress
.\task-cli.bat mark-in-progress 1
Output: 

Marking a task as done
.\task-cli.bat mark-done 1
Output:

Listing all tasks
.\task-cli.bat list
utput: 

Listing tasks by status
.\task-cli.bat list todo
.\task-cli.bat list in-progress
.\task-cli.bat list done
