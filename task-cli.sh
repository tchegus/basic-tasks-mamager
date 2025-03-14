#!/bin/bash

BIN_DIR="bin"

# Check if the required arguments are present
if [ -z "$1" ]; then
    echo "Command is required."
    echo "Usage: task-cli.sh <command> <argument>"
    exit 1
fi

if [ "$1" != "list" ] && [ -z "$2" ]; then
    echo "Argument is required."
    echo "Usage: task-cli.sh <command> <argument>"
    exit 1
fi

# Run the application
echo "Running the application..."
java -cp "$BIN_DIR" Main "$@"