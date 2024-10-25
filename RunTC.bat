@echo off
REM Change directory to the current directory where the .bat file is located
cd /d "%~dp0"

REM Run the Maven verify command
mvn clean verify