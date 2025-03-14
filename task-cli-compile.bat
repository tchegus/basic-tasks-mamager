@echo off
setlocal

REM Set the source and output directories
set SRC_DIR=src
set BIN_DIR=bin

REM Create the output directory if it doesn't exist
if not exist %BIN_DIR% (
    mkdir %BIN_DIR%
)

REM Compile the Java source files
echo Compiling Java source files...
javac -d %BIN_DIR% -sourcepath %SRC_DIR% %SRC_DIR%\com\roadmap\*.java %SRC_DIR%\Main.java

REM Check if the compilation was successful
if %errorlevel% neq 0 (
    echo Compilation failed.
    exit /b %errorlevel%
)

echo Compilation successful.
endlocal