# Script để thiết lập Maven PATH vĩnh viễn
# Chạy PowerShell với quyền Administrator và chạy script này

$mavenPath = "C:\Maven\apache-maven-3.9.8\bin"

# Lấy PATH hiện tại
$currentPath = [Environment]::GetEnvironmentVariable("PATH", "Machine")

# Kiểm tra nếu Maven path chưa có trong PATH
if ($currentPath -notlike "*$mavenPath*") {
    # Thêm Maven vào PATH
    $newPath = "$currentPath;$mavenPath"
    [Environment]::SetEnvironmentVariable("PATH", $newPath, "Machine")
    [Environment]::SetEnvironmentVariable("MAVEN_HOME", "C:\Maven\apache-maven-3.9.8", "Machine")
    
    Write-Host "Đã thêm Maven vào PATH system-wide" -ForegroundColor Green
    Write-Host "Vui lòng khởi động lại PowerShell để sử dụng Maven" -ForegroundColor Yellow
} else {
    Write-Host "Maven đã có trong PATH" -ForegroundColor Green
}

Write-Host "Để sử dụng Maven trong session hiện tại, chạy lệnh:" -ForegroundColor Cyan
Write-Host '$env:PATH += ";C:\Maven\apache-maven-3.9.8\bin"' -ForegroundColor Yellow
