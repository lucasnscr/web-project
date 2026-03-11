@echo off
setlocal EnableExtensions EnableDelayedExpansion
chcp 65001 >nul
title PreventivaDB
color 0B

set "ROOT_DIR=%~dp0"
cd /d "%ROOT_DIR%"

set "LOG_DIR=%ROOT_DIR%logs"
if not exist "%LOG_DIR%" mkdir "%LOG_DIR%"

set "APP_JAR=%ROOT_DIR%backend\target\preventiva-crud.jar"
set "PID_FILE=%LOG_DIR%app.pid"
set "APP_LOG=%LOG_DIR%aplicacao.log"

echo.
echo ==============================================================
echo   PreventivaDB - Inicializacao
echo ==============================================================
echo.

if not exist "%APP_JAR%" (
    echo [ERRO] O arquivo principal da aplicacao nao foi encontrado.
    echo        Execute primeiro o arquivo instalar.bat
    echo.
    pause
    exit /b 1
)

call :resolve_java >nul 2>&1
if errorlevel 1 (
    echo [ERRO] Java nao encontrado.
    echo        Execute primeiro o arquivo instalar.bat
    echo.
    pause
    exit /b 1
)

call :set_java_home >nul 2>&1
call :check_java_version >nul 2>&1
if errorlevel 1 (
    echo [ERRO] Foi encontrado um Java incompatvel.
    echo        Execute novamente o arquivo instalar.bat
    echo.
    pause
    exit /b 1
)

call :check_existing_instance
if errorlevel 1 (
    echo.
    pause
    exit /b 1
)

if defined APP_ALREADY_RUNNING (
    echo [INFO] O sistema ja estava aberto. O navegador sera aberto agora.
    start "" "http://localhost:8080"
    echo.
    pause
    exit /b 0
)

echo [1/4] Iniciando a aplicacao...
del "%PID_FILE%" >nul 2>&1

powershell -NoProfile -ExecutionPolicy Bypass -Command ^
    "$log = '%APP_LOG%';" ^
    "if (Test-Path $log) { Remove-Item $log -Force };" ^
    "New-Item -ItemType File -Path $log -Force | Out-Null;" ^
    "$cmdArgs = '/c """"%JAVA_EXE%"""" -jar """"%APP_JAR%"""" >> """"%APP_LOG%"""" 2>&1';" ^
    "$p = Start-Process -FilePath 'cmd.exe' -ArgumentList $cmdArgs -WorkingDirectory '%ROOT_DIR%' -WindowStyle Hidden -PassThru;" ^
    "Set-Content -Path '%PID_FILE%' -Value $p.Id"

if errorlevel 1 (
    echo [ERRO] Nao foi possivel iniciar a aplicacao.
    echo.
    pause
    exit /b 1
)

set /p APP_PID=<"%PID_FILE%"

echo [2/4] Aguardando o sistema responder...
call :wait_for_app
if errorlevel 1 (
    echo [ERRO] A aplicacao nao respondeu a tempo.
    if defined APP_PID taskkill /PID %APP_PID% /T /F >nul 2>&1
    del "%PID_FILE%" >nul 2>&1
    echo.
    echo Ultimas linhas do log:
    powershell -NoProfile -ExecutionPolicy Bypass -Command "if (Test-Path '%APP_LOG%') { Get-Content '%APP_LOG%' -Tail 30 }"
    echo.
    pause
    exit /b 1
)

echo [3/4] Abrindo o navegador...
start "" "http://localhost:8080"

echo.
echo [4/4] Sistema pronto para uso.
echo.
echo Endereco:
echo   http://localhost:8080
echo.
echo Nao feche esta janela enquanto estiver usando o sistema.
echo Quando terminar, pressione qualquer tecla para encerrar.
echo.
pause >nul

if defined APP_PID (
    echo Encerrando a aplicacao...
    taskkill /PID %APP_PID% /T /F >nul 2>&1
)
del "%PID_FILE%" >nul 2>&1
echo Sistema encerrado.
timeout /t 2 /nobreak >nul
exit /b 0

:wait_for_app
set /a ATTEMPT=0
:wait_loop
set /a ATTEMPT+=1
powershell -NoProfile -ExecutionPolicy Bypass -Command "try { Invoke-WebRequest -Uri 'http://localhost:8080/api/preventivas/stats' -TimeoutSec 3 | Out-Null; exit 0 } catch { exit 1 }" >nul 2>&1
if not errorlevel 1 exit /b 0
if %ATTEMPT% GEQ 60 exit /b 1
timeout /t 2 /nobreak >nul
goto wait_loop

:check_existing_instance
set "APP_ALREADY_RUNNING="
if exist "%PID_FILE%" (
    set /p EXISTING_PID=<"%PID_FILE%"
    tasklist /FI "PID eq !EXISTING_PID!" | findstr /r /c:" !EXISTING_PID! " >nul
    if not errorlevel 1 (
        set "APP_ALREADY_RUNNING=1"
        set "APP_PID=!EXISTING_PID!"
        exit /b 0
    )
    del "%PID_FILE%" >nul 2>&1
)

for /f "tokens=5" %%I in ('netstat -ano ^| findstr ":8080" ^| findstr "LISTENING"') do (
    echo [ERRO] A porta 8080 ja esta sendo usada por outro programa ^(PID %%I^).
    echo        Feche esse programa e tente novamente.
    exit /b 1
)
exit /b 0

:resolve_java
set "JAVA_EXE="
for /f "delims=" %%I in ('where java.exe 2^>nul') do if not defined JAVA_EXE set "JAVA_EXE=%%I"
if not defined JAVA_EXE for /f "delims=" %%I in ('dir /b /s "C:\Program Files\Microsoft\jdk-*\bin\java.exe" 2^>nul') do if not defined JAVA_EXE set "JAVA_EXE=%%I"
if not defined JAVA_EXE for /f "delims=" %%I in ('dir /b /s "C:\Program Files\Eclipse Adoptium\jdk-*\bin\java.exe" 2^>nul') do if not defined JAVA_EXE set "JAVA_EXE=%%I"
if defined JAVA_EXE exit /b 0
exit /b 1

:set_java_home
if not defined JAVA_EXE exit /b 1
for %%I in ("%JAVA_EXE%") do set "JAVA_BIN_DIR=%%~dpI"
for %%I in ("%JAVA_BIN_DIR%..") do set "JAVA_HOME=%%~fI"
exit /b 0

:check_java_version
if not defined JAVA_EXE exit /b 1
"%JAVA_EXE%" -version 2>&1 | findstr /r /c:" version \"2[1-9]\." /c:"openjdk 2[1-9]\." >nul
if errorlevel 1 exit /b 1
exit /b 0
