@echo off
chcp 65001 >nul
title Instalador - Sistema de Manutencao Preventiva
color 0A

echo.
echo ╔══════════════════════════════════════════════════════════════╗
echo ║     INSTALADOR - Sistema de Manutencao Preventiva           ║
echo ╠══════════════════════════════════════════════════════════════╣
echo ║  Este script ira instalar todas as dependencias necessarias ║
echo ╚══════════════════════════════════════════════════════════════╝
echo.
echo Pressione qualquer tecla para iniciar a instalacao...
pause >nul

echo.
echo [1/5] Verificando winget (gerenciador de pacotes do Windows)...
winget --version >nul 2>&1
if %errorlevel% neq 0 (
    echo.
    echo ╔══════════════════════════════════════════════════════════════╗
    echo ║  ERRO: winget nao encontrado!                               ║
    echo ║                                                              ║
    echo ║  O winget e necessario para instalar as dependencias.       ║
    echo ║  Ele ja vem instalado no Windows 10/11 atualizado.          ║
    echo ║                                                              ║
    echo ║  Se voce nao tem, atualize o Windows ou instale o           ║
    echo ║  "Instalador de Aplicativo" pela Microsoft Store.           ║
    echo ╚══════════════════════════════════════════════════════════════╝
    echo.
    pause
    exit /b 1
)
echo     [OK] winget encontrado!

echo.
echo [2/5] Verificando/Instalando Java 21...
java --version 2>nul | findstr "21" >nul
if %errorlevel% neq 0 (
    echo     Java 21 nao encontrado. Instalando...
    winget install --id Microsoft.OpenJDK.21 -e --accept-source-agreements --accept-package-agreements
    if %errorlevel% neq 0 (
        echo     [AVISO] Falha ao instalar Java. Tentando alternativa...
        winget install --id EclipseAdoptium.Temurin.21.JDK -e --accept-source-agreements --accept-package-agreements
    )
    echo.
    echo     [INFO] Java instalado! Voce precisara REINICIAR este script
    echo            apos a instalacao para que o Java seja reconhecido.
) else (
    echo     [OK] Java 21 ja esta instalado!
)

echo.
echo [3/5] Verificando/Instalando Maven...
mvn --version >nul 2>&1
if %errorlevel% neq 0 (
    echo     Maven nao encontrado. Instalando...
    winget install --id Apache.Maven -e --accept-source-agreements --accept-package-agreements
    echo     [INFO] Maven instalado!
) else (
    echo     [OK] Maven ja esta instalado!
)

echo.
echo [4/5] Verificando/Instalando Node.js...
node --version >nul 2>&1
if %errorlevel% neq 0 (
    echo     Node.js nao encontrado. Instalando...
    winget install --id OpenJS.NodeJS.LTS -e --accept-source-agreements --accept-package-agreements
    echo     [INFO] Node.js instalado!
) else (
    echo     [OK] Node.js ja esta instalado!
)

echo.
echo [5/5] Verificando/Instalando pnpm...
call pnpm --version >nul 2>&1
if %errorlevel% neq 0 (
    echo     pnpm nao encontrado. Instalando...
    call npm install -g pnpm
    echo     [INFO] pnpm instalado!
) else (
    echo     [OK] pnpm ja esta instalado!
)

echo.
echo ══════════════════════════════════════════════════════════════
echo.
echo [6/6] Instalando dependencias do projeto...
echo.

cd /d "%~dp0"

echo     Instalando dependencias do Frontend...
cd frontend
call pnpm install
cd ..

echo.
echo ╔══════════════════════════════════════════════════════════════╗
echo ║                    INSTALACAO CONCLUIDA!                     ║
echo ╠══════════════════════════════════════════════════════════════╣
echo ║                                                              ║
echo ║  IMPORTANTE: Feche esta janela e REINICIE o computador      ║
echo ║  para garantir que todas as instalacoes funcionem.          ║
echo ║                                                              ║
echo ║  Apos reiniciar, execute o arquivo "iniciar.bat"            ║
echo ║  para abrir o sistema.                                       ║
echo ║                                                              ║
echo ╚══════════════════════════════════════════════════════════════╝
echo.
pause
