@echo off
setlocal EnableExtensions EnableDelayedExpansion
chcp 65001 >nul
title Instalacao - PreventivaDB
color 0A

set "ROOT_DIR=%~dp0"
cd /d "%ROOT_DIR%"

set "LOG_DIR=%ROOT_DIR%logs"
if not exist "%LOG_DIR%" mkdir "%LOG_DIR%"
set "INSTALL_LOG=%LOG_DIR%instalacao.log"

> "%INSTALL_LOG%" echo ==== Instalacao iniciada em %date% %time% ====

echo.
echo ==============================================================
echo   PreventivaDB - Instalacao para Windows
echo ==============================================================
echo.
echo Este processo vai:
echo   1. Instalar Java 21, Node.js ^(com npm^) e Maven
echo   2. Instalar as dependencias do frontend
echo   3. Gerar a interface web
echo   4. Criar um unico arquivo .jar para a aplicacao
echo.
echo A instalacao precisa de internet e pode levar alguns minutos.
echo.
pause

call :check_winget || goto :fail
call :ensure_java || goto :fail
call :ensure_node || goto :fail
call :ensure_maven || goto :fail
call :refresh_path
call :resolve_java || goto :fail
call :resolve_node || goto :fail
call :resolve_npx || goto :fail
call :set_java_home || goto :fail

echo.
echo [5/7] Instalando dependencias do frontend...
pushd "%ROOT_DIR%frontend"
call "%NPX_CMD%" -y pnpm@10.18.3 install --frozen-lockfile >> "%INSTALL_LOG%" 2>&1
if errorlevel 1 (
    echo     [AVISO] Falha com install travado pelo lockfile. Tentando novamente...
    call "%NPX_CMD%" -y pnpm@10.18.3 install >> "%INSTALL_LOG%" 2>&1
    if errorlevel 1 (
        popd
        goto :fail
    )
)
popd
echo     [OK] Dependencias do frontend instaladas.

echo.
echo [6/7] Gerando a interface web...
pushd "%ROOT_DIR%frontend"
call "%NPX_CMD%" -y pnpm@10.18.3 build >> "%INSTALL_LOG%" 2>&1
if errorlevel 1 (
    popd
    goto :fail
)
popd
echo     [OK] Frontend compilado.

echo.
echo [7/7] Gerando o pacote final da aplicacao...
call "%ROOT_DIR%backend\mvnw.cmd" -DskipTests clean package >> "%INSTALL_LOG%" 2>&1
if errorlevel 1 goto :fail
if not exist "%ROOT_DIR%backend\target\preventiva-crud.jar" goto :fail
echo     [OK] Arquivo gerado em backend\target\preventiva-crud.jar

echo.
echo ==============================================================
echo   Instalacao concluida com sucesso
echo ==============================================================
echo.
echo Para abrir o sistema, execute o arquivo:
echo   iniciar.bat
echo.
echo O sistema sera aberto em:
echo   http://localhost:8080
echo.
echo Se algo der errado, o log completo esta em:
echo   logs\instalacao.log
echo.
pause
exit /b 0

:fail
echo.
echo ==============================================================
echo   Falha na instalacao
echo ==============================================================
echo.
echo Nao foi possivel concluir a instalacao.
echo.
echo Veja o log em:
echo   logs\instalacao.log
echo.
pause
exit /b 1

:check_winget
echo.
echo [1/7] Verificando o winget...
winget --version >> "%INSTALL_LOG%" 2>&1
if errorlevel 1 (
    echo     [ERRO] O winget nao foi encontrado.
    echo     Atualize o Windows ou instale o "App Installer" da Microsoft.
    exit /b 1
)
echo     [OK] winget encontrado.
exit /b 0

:ensure_java
echo.
echo [2/7] Verificando Java 21...
call :resolve_java >nul 2>&1
if defined JAVA_EXE (
    call :check_java_version >nul 2>&1
    if not errorlevel 1 (
        echo     [OK] Java compativel encontrado.
        exit /b 0
    )
)

echo     Instalando Java 21...
winget install --id Microsoft.OpenJDK.21 -e --source winget --accept-source-agreements --accept-package-agreements --silent >> "%INSTALL_LOG%" 2>&1
if errorlevel 1 (
    echo     Tentando alternativa de Java 21...
    winget install --id EclipseAdoptium.Temurin.21.JDK -e --source winget --accept-source-agreements --accept-package-agreements --silent >> "%INSTALL_LOG%" 2>&1
    if errorlevel 1 exit /b 1
)

call :refresh_path
call :resolve_java >nul 2>&1 || exit /b 1
call :check_java_version >nul 2>&1 || exit /b 1
echo     [OK] Java instalado.
exit /b 0

:ensure_node
echo.
echo [3/7] Verificando Node.js...
call :resolve_node >nul 2>&1
if defined NODE_EXE (
    for /f %%I in ('"%NODE_EXE%" -p "process.versions.node.split('.')[0]" 2^>nul') do set "NODE_MAJOR=%%I"
    if defined NODE_MAJOR if !NODE_MAJOR! GEQ 20 (
        echo     [OK] Node.js compativel encontrado.
        exit /b 0
    )
)

echo     Instalando Node.js LTS...
winget install --id OpenJS.NodeJS.LTS -e --source winget --accept-source-agreements --accept-package-agreements --silent >> "%INSTALL_LOG%" 2>&1
if errorlevel 1 exit /b 1

call :refresh_path
call :resolve_node >nul 2>&1 || exit /b 1
for /f %%I in ('"%NODE_EXE%" -p "process.versions.node.split('.')[0]" 2^>nul') do set "NODE_MAJOR=%%I"
if not defined NODE_MAJOR exit /b 1
if !NODE_MAJOR! LSS 20 exit /b 1
echo     [OK] Node.js instalado.
exit /b 0

:ensure_maven
echo.
echo [4/7] Verificando Maven...
call :resolve_maven >nul 2>&1
if defined MAVEN_CMD (
    echo     [OK] Maven encontrado.
    exit /b 0
)

echo     Instalando Maven...
winget install --id Apache.Maven -e --source winget --accept-source-agreements --accept-package-agreements --silent >> "%INSTALL_LOG%" 2>&1
if errorlevel 1 exit /b 1

call :refresh_path
call :resolve_maven >nul 2>&1 || exit /b 1
echo     [OK] Maven instalado.
exit /b 0

:refresh_path
for /f "usebackq delims=" %%I in (`powershell -NoProfile -Command "[Environment]::GetEnvironmentVariable('Path','Machine') + ';' + [Environment]::GetEnvironmentVariable('Path','User')"`) do set "PATH=%%I"
exit /b 0

:resolve_java
set "JAVA_EXE="
for /f "delims=" %%I in ('where java.exe 2^>nul') do if not defined JAVA_EXE set "JAVA_EXE=%%I"
if not defined JAVA_EXE for /f "delims=" %%I in ('dir /b /s "C:\Program Files\Microsoft\jdk-*\bin\java.exe" 2^>nul') do if not defined JAVA_EXE set "JAVA_EXE=%%I"
if not defined JAVA_EXE for /f "delims=" %%I in ('dir /b /s "C:\Program Files\Eclipse Adoptium\jdk-*\bin\java.exe" 2^>nul') do if not defined JAVA_EXE set "JAVA_EXE=%%I"
if defined JAVA_EXE exit /b 0
exit /b 1

:resolve_node
set "NODE_EXE="
for /f "delims=" %%I in ('where node.exe 2^>nul') do if not defined NODE_EXE set "NODE_EXE=%%I"
if not defined NODE_EXE if exist "C:\Program Files\nodejs\node.exe" set "NODE_EXE=C:\Program Files\nodejs\node.exe"
if defined NODE_EXE exit /b 0
exit /b 1

:resolve_npx
set "NPX_CMD="
for /f "delims=" %%I in ('where npx.cmd 2^>nul') do if not defined NPX_CMD set "NPX_CMD=%%I"
if not defined NPX_CMD if defined NODE_EXE (
    for %%I in ("%NODE_EXE%") do if exist "%%~dpInpx.cmd" set "NPX_CMD=%%~dpInpx.cmd"
)
if defined NPX_CMD exit /b 0
exit /b 1

:resolve_maven
set "MAVEN_CMD="
for /f "delims=" %%I in ('where mvn.cmd 2^>nul') do if not defined MAVEN_CMD set "MAVEN_CMD=%%I"
if not defined MAVEN_CMD for /f "delims=" %%I in ('dir /b /s "C:\Program Files\Apache\maven-*\bin\mvn.cmd" 2^>nul') do if not defined MAVEN_CMD set "MAVEN_CMD=%%I"
if not defined MAVEN_CMD for /f "delims=" %%I in ('dir /b /s "C:\Program Files\Apache Maven\bin\mvn.cmd" 2^>nul') do if not defined MAVEN_CMD set "MAVEN_CMD=%%I"
if defined MAVEN_CMD exit /b 0
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
