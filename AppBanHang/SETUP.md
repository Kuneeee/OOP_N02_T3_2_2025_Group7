# Hướng dẫn cài đặt và chạy ứng dụng

## Bước 1: Cài đặt Java JDK 17+
- Tải và cài đặt Java JDK 17 từ: https://adoptium.net/
- Kiểm tra cài đặt: `java -version`

## Bước 2: Cài đặt Maven
### Cách 1: Tự động (đã có script sẵn)
```powershell
# Chạy trong PowerShell
.\install-maven.ps1
```

### Cách 2: Thủ công
- Tải Maven từ: https://maven.apache.org/download.cgi
- Giải nén vào C:\Maven
- Chạy script: `.\setup-maven-path.ps1` (với quyền Administrator)
- Kiểm tra cài đặt: `mvn -version`

### Cách 3: Sử dụng trong session hiện tại
```powershell
$env:PATH += ";C:\Maven\apache-maven-3.9.8\bin"
mvn -version
```

## Bước 3: Cài đặt MySQL (TÙY CHỌN)
⚠️ **Lưu ý:** Bạn có thể bỏ qua MySQL và sử dụng H2 Database (in-memory) để test nhanh

### Cách A: Sử dụng H2 Database (Đơn giản - Không cần MySQL)
1. Mở file `application.properties`
2. Comment (thêm # phía trước) các dòng MySQL:
```properties
# spring.datasource.url=jdbc:mysql://localhost:3306/taphoa_db?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
# spring.datasource.username=root
# spring.datasource.password=
# spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
# spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
```

3. Thêm cấu hình H2:
```properties
# H2 Database Configuration
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.h2.console.enabled=true
```

### Cách B: Sử dụng MySQL (Cần cài đặt)
- Tải và cài đặt MySQL 8.0+
- Tạo database: 
```sql
CREATE DATABASE taphoa_db;
```
- Cập nhật username/password trong application.properties

## Bước 4: QUAN TRỌNG - Thiết lập Maven
```powershell
# Mở PowerShell trong thư mục dự án và chạy:
$env:PATH += ";C:\Maven\apache-maven-3.9.8\bin"

# Kiểm tra Maven hoạt động:
mvn -version
```

## Bước 5: Chạy ứng dụng
```powershell
# Đảm bảo bạn đang ở thư mục dự án (AppBanHang)
cd C:\Users\Kune\Documents\GitHub\AppBanHang

# Chạy lệnh sau để khởi động ứng dụng:
mvn spring-boot:run
```

## Bước 6: Truy cập ứng dụng
1. **Chờ ứng dụng khởi động hoàn tất** (sẽ thấy thông báo: `Started AppBanHangApplication`)
2. **Mở trình duyệt** (Chrome, Firefox, Edge...)
3. **Truy cập:** http://localhost:8080

## Các trang chính:
- Trang chủ: http://localhost:8080/
- Dashboard: http://localhost:8080/dashboard  
- Quản lý hàng hóa: http://localhost:8080/hanghoa
- Nhập hàng: http://localhost:8080/nhap
- Xuất bán: http://localhost:8080/xuatban
- Báo cáo doanh thu: http://localhost:8080/doanhthu

## Khắc phục sự cố:

### ❌ Lỗi "mvn không được nhận dạng":
```powershell
$env:PATH += ";C:\Maven\apache-maven-3.9.8\bin"
```

### ❌ Lỗi kết nối Database:
- Sử dụng H2 Database (như hướng dẫn Cách A ở trên)
- Hoặc cài đặt MySQL và tạo database

### ❌ Port 8080 đã được sử dụng:
Trong `application.properties`, thay đổi port:
```properties
server.port=8081
```
Sau đó truy cập: http://localhost:8081

### ❌ Ứng dụng không khởi động:
```powershell
# Thử build trước:
mvn clean compile

# Nếu có lỗi, chạy:
mvn clean install -DskipTests
```
