#Requires -Version 5.1

# ── Auto-elevar para administrador ──────────────────────────────
if (-not ([Security.Principal.WindowsPrincipal][Security.Principal.WindowsIdentity]::GetCurrent()).IsInRole([Security.Principal.WindowsBuiltInRole]::Administrator)) {
    Start-Process PowerShell -Verb RunAs -ArgumentList "-NoProfile -ExecutionPolicy Bypass -File `"$PSCommandPath`""
    exit
}

[Console]::OutputEncoding = [System.Text.Encoding]::UTF8
$host.UI.RawUI.WindowTitle = "Instalador - Sistema de Manutencao Preventiva"

# ── Helpers ──────────────────────────────────────────────────────
function Write-Ok($msg)   { Write-Host "    [OK] $msg"   -ForegroundColor Green  }
function Write-Info($msg) { Write-Host "    ...  $msg"   -ForegroundColor Yellow }
function Write-Fail($msg) { Write-Host "    [ERRO] $msg" -ForegroundColor Red    }

function Refresh-Path {
    $m = [Environment]::GetEnvironmentVariable("PATH", "Machine")
    $u = [Environment]::GetEnvironmentVariable("PATH", "User")
    $env:PATH = "$m;$u"
}

function Has-Command($cmd) {
    return [bool](Get-Command $cmd -ErrorAction SilentlyContinue)
}

function Try-Winget($id) {
    winget install --id $id -e --accept-source-agreements --accept-package-agreements --silent 2>&1 | Out-Null
    return $LASTEXITCODE -eq 0
}

function Download($url, $dest) {
    Write-Info "Baixando $(Split-Path $dest -Leaf)..."
    Invoke-WebRequest -Uri $url -OutFile $dest -UseBasicParsing
}

function Install-Msi($path) {
    Start-Process msiexec.exe -Wait -ArgumentList "/i `"$path`" /quiet /norestart"
    Remove-Item $path -Force -ErrorAction SilentlyContinue
}

# ── Cabecalho ────────────────────────────────────────────────────
Write-Host ""
Write-Host "╔══════════════════════════════════════════════════════════════╗" -ForegroundColor Cyan
Write-Host "║     INSTALADOR - Sistema de Manutencao Preventiva           ║" -ForegroundColor Cyan
Write-Host "╠══════════════════════════════════════════════════════════════╣" -ForegroundColor Cyan
Write-Host "║  Instalando: Java 21, Maven, Node.js, pnpm                  ║" -ForegroundColor Cyan
Write-Host "╚══════════════════════════════════════════════════════════════╝" -ForegroundColor Cyan
Write-Host ""
Read-Host "Pressione Enter para iniciar"

$useWinget = Has-Command "winget"
if ($useWinget) { Write-Info "winget detectado" } else { Write-Info "winget nao disponivel - usando download direto" }

# ============================================================
# [1/5] JAVA 21
# ============================================================
Write-Host ""
Write-Host "[1/5] Java 21..." -ForegroundColor White

$javaInstalled = $false
try { $javaInstalled = (& java -version 2>&1) -match '"21' } catch {}

if ($javaInstalled) {
    Write-Ok "Java 21 ja instalado"
} else {
    Write-Info "Instalando Java 21..."
    $ok = $false

    if ($useWinget) {
        $ok = Try-Winget "Microsoft.OpenJDK.21"
        if (-not $ok) { $ok = Try-Winget "EclipseAdoptium.Temurin.21.JDK" }
    }

    if (-not $ok) {
        $msi = "$env:TEMP\jdk21.msi"
        Download "https://aka.ms/download-jdk/microsoft-jdk-21-windows-x64.msi" $msi
        Install-Msi $msi
    }

    Refresh-Path
    Write-Ok "Java 21 instalado"
}

# ============================================================
# [2/5] MAVEN
# ============================================================
Write-Host ""
Write-Host "[2/5] Maven..." -ForegroundColor White

if (Has-Command "mvn") {
    Write-Ok "Maven ja instalado"
} else {
    Write-Info "Instalando Maven..."

    if ($useWinget) {
        Try-Winget "Apache.Maven" | Out-Null
        Refresh-Path
    }

    if (-not (Has-Command "mvn")) {
        $zip  = "$env:TEMP\maven.zip"
        $dest = "C:\Program Files\Maven"
        Download "https://archive.apache.org/dist/maven/maven-3/3.9.6/binaries/apache-maven-3.9.6-bin.zip" $zip
        Write-Info "Extraindo Maven..."
        Expand-Archive -Path $zip -DestinationPath $dest -Force
        Remove-Item $zip -Force -ErrorAction SilentlyContinue
        [Environment]::SetEnvironmentVariable("MAVEN_HOME", "$dest\apache-maven-3.9.6", "Machine")
        $p = [Environment]::GetEnvironmentVariable("PATH", "Machine")
        [Environment]::SetEnvironmentVariable("PATH", "$p;$dest\apache-maven-3.9.6\bin", "Machine")
        Refresh-Path
    }

    Write-Ok "Maven instalado"
}

# ============================================================
# [3/5] NODE.JS
# ============================================================
Write-Host ""
Write-Host "[3/5] Node.js..." -ForegroundColor White

if (Has-Command "node") {
    Write-Ok "Node.js ja instalado"
} else {
    Write-Info "Instalando Node.js LTS..."
    $ok = $false

    if ($useWinget) {
        $ok = Try-Winget "OpenJS.NodeJS.LTS"
        Refresh-Path
    }

    if (-not (Has-Command "node")) {
        Write-Info "Buscando versao LTS em nodejs.org..."
        $ver = (Invoke-WebRequest "https://nodejs.org/dist/index.json" -UseBasicParsing |
                ConvertFrom-Json | Where-Object { $_.lts } | Select-Object -First 1).version
        $msi = "$env:TEMP\nodejs.msi"
        Download "https://nodejs.org/dist/$ver/node-$ver-x64.msi" $msi
        Install-Msi $msi
        Refresh-Path
    }

    # Garantir que o path do Node esta na sessao atual
    $nodePath = "$env:ProgramFiles\nodejs"
    if ((Test-Path "$nodePath\node.exe") -and ($env:PATH -notlike "*$nodePath*")) {
        $env:PATH = "$env:PATH;$nodePath"
    }

    Write-Ok "Node.js instalado"
}

# ============================================================
# [4/5] PNPM
# ============================================================
Write-Host ""
Write-Host "[4/5] pnpm..." -ForegroundColor White

if (Has-Command "pnpm") {
    Write-Ok "pnpm ja instalado"
} else {
    Write-Info "Instalando pnpm via npm..."
    npm install -g pnpm
    if ($LASTEXITCODE -ne 0) {
        Write-Fail "Falha ao instalar pnpm. Verifique se Node.js foi instalado."
        Read-Host "Pressione Enter para sair"
        exit 1
    }
    Write-Ok "pnpm instalado"
}

# ============================================================
# [5/5] DEPENDENCIAS DO PROJETO
# ============================================================
Write-Host ""
Write-Host "══════════════════════════════════════════════════════════════" -ForegroundColor Cyan
Write-Host ""
Write-Host "[5/5] Instalando dependencias do projeto..." -ForegroundColor White
Write-Host ""

Set-Location (Split-Path -Parent $MyInvocation.MyCommand.Path)

Write-Info "Frontend (pnpm install)..."
Set-Location "frontend"
& pnpm install
$exitCode = $LASTEXITCODE
Set-Location ".."

if ($exitCode -ne 0) {
    Write-Fail "Falha ao instalar dependencias do frontend!"
    Read-Host "Pressione Enter para sair"
    exit 1
}

Write-Ok "Dependencias instaladas"

# ── Conclusao ────────────────────────────────────────────────────
Write-Host ""
Write-Host "╔══════════════════════════════════════════════════════════════╗" -ForegroundColor Green
Write-Host "║                  INSTALACAO CONCLUIDA!                      ║" -ForegroundColor Green
Write-Host "╠══════════════════════════════════════════════════════════════╣" -ForegroundColor Green
Write-Host "║                                                              ║" -ForegroundColor Green
Write-Host "║  REINICIE o computador e depois execute 'iniciar.bat'       ║" -ForegroundColor Green
Write-Host "║                                                              ║" -ForegroundColor Green
Write-Host "╚══════════════════════════════════════════════════════════════╝" -ForegroundColor Green
Write-Host ""
Read-Host "Pressione Enter para fechar"
