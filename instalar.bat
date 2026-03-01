@echo off
chcp 65001 >nul
net session >nul 2>&1
if %errorlevel% equ 0 goto :run
echo Solicitando permissao de administrador...
powershell -Command "Start-Process -FilePath '%~f0' -Verb RunAs -Wait"
exit /b
:run
powershell -NoProfile -ExecutionPolicy Bypass -File "%~dp0instalar.ps1"
if %errorlevel% neq 0 pause
