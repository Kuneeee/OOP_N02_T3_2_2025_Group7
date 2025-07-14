# Script cài đặt Maven cho Windows
# Chạy script này trong PowerShell với quyền Administrator

param(
    [string]$InstallPath = "C:\Program Files\Maven"
)

$ErrorActionPreference = "Stop"

Write-Host "Bắt đầu cài đặt Apache Maven..." -ForegroundColor Green

# Kiểm tra Java
try {
    $javaVersion = java -version 2>&1
    Write-Host "Java đã được cài đặt: $($javaVersion[0])" -ForegroundColor Green
} catch {
    Write-Host "Java không được tìm thấy. Vui lòng cài đặt Java trước." -ForegroundColor Red
    exit 1
}

# Tạo thư mục cài đặt
if (!(Test-Path $InstallPath)) {
    New-Item -ItemType Directory -Force -Path $InstallPath | Out-Null
    Write-Host "Đã tạo thư mục: $InstallPath" -ForegroundColor Yellow
}

# Tải Maven
$mavenVersion = "3.9.8"
$downloadUrl = "https://archive.apache.org/dist/maven/maven-3/$mavenVersion/binaries/apache-maven-$mavenVersion-bin.zip"
$zipFile = "$env:TEMP\apache-maven-$mavenVersion-bin.zip"

Write-Host "Đang tải Maven $mavenVersion..." -ForegroundColor Yellow
try {
    Invoke-WebRequest -Uri $downloadUrl -OutFile $zipFile -UseBasicParsing
    Write-Host "Đã tải xong Maven" -ForegroundColor Green
} catch {
    Write-Host "Lỗi khi tải Maven: $($_.Exception.Message)" -ForegroundColor Red
    exit 1
}

# Giải nén
Write-Host "Đang giải nén Maven..." -ForegroundColor Yellow
try {
    Expand-Archive -Path $zipFile -DestinationPath $InstallPath -Force
    $mavenHome = "$InstallPath\apache-maven-$mavenVersion"
    Write-Host "Đã giải nén Maven vào: $mavenHome" -ForegroundColor Green
} catch {
    Write-Host "Lỗi khi giải nén: $($_.Exception.Message)" -ForegroundColor Red
    exit 1
}

# Cập nhật PATH
$currentPath = [Environment]::GetEnvironmentVariable("PATH", "Machine")
$mavenBin = "$mavenHome\bin"

if ($currentPath -notlike "*$mavenBin*") {
    Write-Host "Đang cập nhật biến môi trường PATH..." -ForegroundColor Yellow
    try {
        [Environment]::SetEnvironmentVariable("PATH", "$currentPath;$mavenBin", "Machine")
        [Environment]::SetEnvironmentVariable("MAVEN_HOME", $mavenHome, "Machine")
        Write-Host "Đã cập nhật PATH và MAVEN_HOME" -ForegroundColor Green
    } catch {
        Write-Host "Lỗi khi cập nhật PATH: $($_.Exception.Message)" -ForegroundColor Red
        Write-Host "Vui lòng thêm thủ công: $mavenBin vào PATH" -ForegroundColor Yellow
    }
}

# Dọn dẹp
Remove-Item $zipFile -Force -ErrorAction SilentlyContinue

Write-Host "`nCài đặt Maven hoàn tất!" -ForegroundColor Green
Write-Host "Maven Home: $mavenHome" -ForegroundColor Cyan
Write-Host "Maven Bin: $mavenBin" -ForegroundColor Cyan
Write-Host "`nVui lòng khởi động lại PowerShell để sử dụng Maven" -ForegroundColor Yellow
Write-Host "Hoặc chạy lệnh sau để cập nhật PATH trong session hiện tại:" -ForegroundColor Yellow
Write-Host "`$env:PATH += ';$mavenBin'" -ForegroundColor Cyan
