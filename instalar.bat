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

:: ── Verificar privilegios de administrador ──────────────────────
net session >nul 2>&1
if %errorlevel% neq 0 (
    echo  [ERRO] Execute como Administrador!
    echo.
    echo  Feche esta janela, clique com o botao direito em
    echo  "instalar.bat" e escolha "Executar como administrador"
    echo.
    pause
    exit /b 1
)

echo Pressione qualquer tecla para iniciar a instalacao...
pause >nul

:: ── Detectar winget ──────────────────────────────────────────────
set USE_WINGET=0
winget --version >nul 2>&1
if %errorlevel% equ 0 (
    set USE_WINGET=1
    echo     [INFO] winget detectado - usando instalacao via winget
) else (
    echo     [INFO] winget nao encontrado - usando download direto
)

:: ============================================================
:: [1/5] JAVA 21
:: ============================================================
echo.
echo [1/5] Verificando Java 21...

java -version 2>&1 | findstr /i "\"21" >nul
if %errorlevel% equ 0 (
    echo     [OK] Java 21 ja esta instalado!
    goto :check_maven
)

echo     Java 21 nao encontrado. Instalando...

if %USE_WINGET% equ 1 (
    winget install --id Microsoft.OpenJDK.21 -e --accept-source-agreements --accept-package-agreements --silent
    if %errorlevel% neq 0 (
        echo     Tentando alternativa (Adoptium Temurin)...
        winget install --id EclipseAdoptium.Temurin.21.JDK -e --accept-source-agreements --accept-package-agreements --silent
    )
) else (
    echo     Baixando JDK 21 da Microsoft...
    set "_JAVA_MSI=%TEMP%\jdk21.msi"
    powershell -NoProfile -ExecutionPolicy Bypass -Command "Invoke-WebRequest -Uri 'https://aka.ms/download-jdk/microsoft-jdk-21-windows-x64.msi' -OutFile '%_JAVA_MSI%' -UseBasicParsing"
    echo     Instalando JDK 21...
    msiexec /i "%_JAVA_MSI%" /quiet /norestart
    del "%_JAVA_MSI%" >nul 2>&1
)

call :refresh_path
java -version >nul 2>&1
if %errorlevel% equ 0 (
    echo     [OK] Java 21 instalado com sucesso!
) else (
    echo     [INFO] Java instalado. PATH sera reconhecido apos reiniciar.
)

:check_maven
:: ============================================================
:: [2/5] MAVEN
:: ============================================================
echo.
echo [2/5] Verificando Maven...

mvn --version >nul 2>&1
if %errorlevel% equ 0 (
    echo     [OK] Maven ja esta instalado!
    goto :check_node
)

echo     Maven nao encontrado. Instalando...

if %USE_WINGET% equ 1 (
    winget install --id Apache.Maven -e --accept-source-agreements --accept-package-agreements --silent
    call :refresh_path
)

mvn --version >nul 2>&1
if %errorlevel% neq 0 (
    echo     Baixando Maven 3.9.6 diretamente...
    set "_MVN_ZIP=%TEMP%\maven.zip"
    powershell -NoProfile -ExecutionPolicy Bypass -Command "Invoke-WebRequest -Uri 'https://archive.apache.org/dist/maven/maven-3/3.9.6/binaries/apache-maven-3.9.6-bin.zip' -OutFile '%_MVN_ZIP%' -UseBasicParsing"
    powershell -NoProfile -ExecutionPolicy Bypass -Command "Expand-Archive -Path '%_MVN_ZIP%' -DestinationPath 'C:\Program Files\Maven' -Force"
    del "%_MVN_ZIP%" >nul 2>&1
    setx /M MAVEN_HOME "C:\Program Files\Maven\apache-maven-3.9.6" >nul
    powershell -NoProfile -ExecutionPolicy Bypass -Command "$p=[Environment]::GetEnvironmentVariable('PATH','Machine'); [Environment]::SetEnvironmentVariable('PATH',$p+';C:\Program Files\Maven\apache-maven-3.9.6\bin','Machine')"
    call :refresh_path
)

echo     [OK] Maven instalado!

:check_node
:: ============================================================
:: [3/5] NODE.JS
:: ============================================================
echo.
echo [3/5] Verificando Node.js...

node --version >nul 2>&1
if %errorlevel% equ 0 (
    echo     [OK] Node.js ja esta instalado!
    goto :check_pnpm
)

echo     Node.js nao encontrado. Instalando...

if %USE_WINGET% equ 1 (
    winget install --id OpenJS.NodeJS.LTS -e --accept-source-agreements --accept-package-agreements --silent
) else (
    echo     Buscando versao LTS mais recente do Node.js...
    set "_NODE_MSI=%TEMP%\nodejs.msi"
    powershell -NoProfile -ExecutionPolicy Bypass -Command "$v=(Invoke-WebRequest 'https://nodejs.org/dist/index.json' -UseBasicParsing|ConvertFrom-Json|?{$_.lts}|Select-Object -First 1).version; $url='https://nodejs.org/dist/'+$v+'/node-'+$v+'-x64.msi'; Write-Host ('    Baixando Node.js '+$v+'...'); Invoke-WebRequest -Uri $url -OutFile '%_NODE_MSI%' -UseBasicParsing"
    echo     Instalando Node.js...
    msiexec /i "%_NODE_MSI%" /quiet /norestart
    del "%_NODE_MSI%" >nul 2>&1
)

call :refresh_path
if exist "%ProgramFiles%\nodejs\node.exe" set "PATH=%PATH%;%ProgramFiles%\nodejs"
echo     [OK] Node.js instalado!

:check_pnpm
:: ============================================================
:: [4/5] PNPM
:: ============================================================
echo.
echo [4/5] Verificando pnpm...

call pnpm --version >nul 2>&1
if %errorlevel% equ 0 (
    echo     [OK] pnpm ja esta instalado!
    goto :install_deps
)

echo     pnpm nao encontrado. Instalando...
call npm install -g pnpm
if %errorlevel% neq 0 (
    echo.
    echo     [ERRO] Falha ao instalar pnpm!
    echo     Verifique se o Node.js foi instalado corretamente.
    pause
    exit /b 1
)
echo     [OK] pnpm instalado!

:install_deps
:: ============================================================
:: [5/5] DEPENDENCIAS DO PROJETO
:: ============================================================
echo.
echo ══════════════════════════════════════════════════════════════
echo.
echo [5/5] Instalando dependencias do projeto...
echo.

cd /d "%~dp0"

echo     Instalando dependencias do Frontend...
cd frontend
call pnpm install
if %errorlevel% neq 0 (
    echo.
    echo     [ERRO] Falha ao instalar dependencias do frontend!
    cd ..
    pause
    exit /b 1
)
cd ..

echo     [OK] Dependencias do Frontend instaladas!

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
exit /b 0

:: ============================================================
:: SUBROTINA: Atualiza PATH da sessao atual lendo o registro
:: ============================================================
:refresh_path
powershell -NoProfile -Command "[Environment]::GetEnvironmentVariable('PATH','Machine')+';'+[Environment]::GetEnvironmentVariable('PATH','User')" > "%TEMP%\_refreshpath.tmp"
for /f "usebackq delims=" %%p in ("%TEMP%\_refreshpath.tmp") do set "PATH=%%p"
del "%TEMP%\_refreshpath.tmp" >nul 2>&1
exit /b 0
