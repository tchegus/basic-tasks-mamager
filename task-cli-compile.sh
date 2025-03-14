#!/bin/bash

# Set the source and output directories
SRC_DIR="src"
BIN_DIR="bin"

# Create the output directory if it doesn't exist
if [ ! -d "$BIN_DIR" ]; then
    mkdir "$BIN_DIR"
fi

# Compile the Java source files
echo "Compiling Java source files..."
javac -d "$BIN_DIR" -sourcepath "$SRC_DIR" "$SRC_DIR/com/roadmap/*.java" "$SRC_DIR/Main.java"

# Check if the compilation was successful
if [ $? -ne 0 ]; then
    echo "Compilation failed."
    exit 1
fi

echo "Compilation successful."