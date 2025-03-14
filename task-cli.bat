@echo off
setlocal

set BIN_DIR=bin

REM Check if the required arguments are present
if "%1"=="" (
    echo Command is required.
    exit /b 1
)

if "%1" neq "list" (
    if "%~2"=="" (
        echo Argument is required.
        echo Usage: task-cli.bat command argument
        exit /b 1
    )
)

REM Run the application
echo Running the application...
java -cp %BIN_DIR% Main %*

endlocal