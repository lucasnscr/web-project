@echo off
chcp 65001 >nul
title Sistema de Manutencao Preventiva
color 0B

cd /d "%~dp0"

echo.
echo ╔══════════════════════════════════════════════════════════════╗
echo ║        Sistema de Manutencao Preventiva - Iniciando         ║
echo ╚══════════════════════════════════════════════════════════════╝
echo.

REM Verificar se Java está instalado
java --version >nul 2>&1
if %errorlevel% neq 0 (
    echo [ERRO] Java nao encontrado!
    echo        Execute primeiro o arquivo "instalar.bat"
    echo.
    pause
    exit /b 1
)

REM Verificar se Node está instalado
node --version >nul 2>&1
if %errorlevel% neq 0 (
    echo [ERRO] Node.js nao encontrado!
    echo        Execute primeiro o arquivo "instalar.bat"
    echo.
    pause
    exit /b 1
)

echo [1/4] Iniciando o servidor (Backend)...
echo       Isso pode demorar alguns segundos na primeira vez...
echo.

REM Iniciar o backend em uma nova janela minimizada
start "Backend - Sistema Preventiva" /min cmd /c "cd /d "%~dp0backend" && mvn spring-boot:run"

echo [2/4] Aguardando o servidor iniciar...
echo.

REM Aguardar o backend ficar pronto (tenta conectar na porta 8080)
:wait_backend
timeout /t 3 /nobreak >nul
curl -s http://localhost:8080/api/preventivas >nul 2>&1
if %errorlevel% neq 0 (
    echo       Ainda carregando... aguarde...
    goto wait_backend
)
echo       [OK] Servidor iniciado!
echo.

echo [3/4] Iniciando a interface (Frontend)...
echo.

REM Iniciar o frontend em uma nova janela minimizada
start "Frontend - Sistema Preventiva" /min cmd /c "cd /d "%~dp0frontend" && pnpm dev"

REM Aguardar o frontend ficar pronto
echo       Aguardando interface carregar...
:wait_frontend
timeout /t 2 /nobreak >nul
curl -s http://localhost:5173 >nul 2>&1
if %errorlevel% neq 0 (
    goto wait_frontend
)
echo       [OK] Interface iniciada!
echo.

echo [4/4] Abrindo o navegador...
echo.

REM Tentar abrir no Chrome, se não encontrar usa o navegador padrão
start "" "chrome" "http://localhost:5173" 2>nul
if %errorlevel% neq 0 (
    start "" "http://localhost:5173"
)

echo ╔══════════════════════════════════════════════════════════════╗
echo ║                   SISTEMA INICIADO COM SUCESSO!              ║
echo ╠══════════════════════════════════════════════════════════════╣
echo ║                                                              ║
echo ║  O sistema foi aberto no navegador.                         ║
echo ║                                                              ║
echo ║  IMPORTANTE: NAO FECHE ESTA JANELA enquanto estiver         ║
echo ║  usando o sistema!                                           ║
echo ║                                                              ║
echo ║  Para ENCERRAR o sistema, feche esta janela e as outras     ║
echo ║  janelas do terminal que foram abertas.                      ║
echo ║                                                              ║
echo ╚══════════════════════════════════════════════════════════════╝
echo.
echo Pressione qualquer tecla para ENCERRAR o sistema...
pause >nul

echo.
echo Encerrando o sistema...

REM Encerrar os processos
taskkill /FI "WINDOWTITLE eq Backend - Sistema Preventiva*" /F >nul 2>&1
taskkill /FI "WINDOWTITLE eq Frontend - Sistema Preventiva*" /F >nul 2>&1

REM Matar processos Java e Node relacionados ao projeto
for /f "tokens=5" %%a in ('netstat -aon ^| findstr ":8080"') do taskkill /PID %%a /F >nul 2>&1
for /f "tokens=5" %%a in ('netstat -aon ^| findstr ":5173"') do taskkill /PID %%a /F >nul 2>&1

echo Sistema encerrado!
timeout /t 2 >nul
