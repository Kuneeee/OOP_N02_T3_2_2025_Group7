# 🚀 HƯỚNG DẪN NHANH CHẠY ỨNG DỤNG DESKTOP

## Các bước thực hiện:

### 1. Mở PowerShell tại thư mục dự án
```powershell
cd C:\Users\Kune\Documents\GitHub\AppBanHang
```

### 2. Thiết lập Maven
```powershell
$env:PATH += ";C:\Maven\apache-maven-3.9.8\bin"
mvn -version
```

### 3. Chạy ứng dụng desktop
```powershell
mvn spring-boot:run
```

### 4. Sử dụng ứng dụng
- Đợi thông báo: `Started AppBanHangApplication in X.XXX seconds`
- Ứng dụng desktop sẽ tự động mở ra với giao diện Swing
- Sử dụng các menu và nút để điều hướng:
  - **Dashboard**: Trang tổng quan
  - **Quản lý hàng hóa**: Thêm, sửa, xóa sản phẩm
  - **Nhập hàng**: Tạo phiếu nhập hàng
  - **Xuất bán**: Tạo phiếu bán hàng
  - **Báo cáo doanh thu**: Xem báo cáo và xuất Excel

## ✅ Nếu thành công:
- Bạn sẽ thấy cửa sổ ứng dụng "Ứng Dụng Quản Lý Tạp Hóa"
- Giao diện hiện đại với Look & Feel FlatLaf
- Có thể điều hướng qua các panel bằng menu hoặc toolbar

## ❌ Nếu có lỗi:

### Lỗi "mvn không được nhận dạng":
```powershell
$env:PATH += ";C:\Maven\apache-maven-3.9.8\bin"
```

### Lỗi build:
```powershell
mvn clean compile
```

### Lỗi hiển thị GUI:
- Đảm bảo Java có hỗ trợ GUI (không phải headless)
- Kiểm tra biến môi trường DISPLAY (trên Linux)

## 🔧 Kiểm tra status:
- Trong PowerShell, nếu ứng dụng đang chạy, bạn sẽ thấy log Spring Boot
- Để dừng: đóng cửa sổ ứng dụng hoặc nhấn `Ctrl + C` trong PowerShell

## � Cơ sở dữ liệu:
- Ứng dụng sử dụng H2 Database (in-memory) cho demo
- Dữ liệu sẽ mất khi tắt ứng dụng
- Có thể cấu hình MySQL trong `application.properties` để lưu trữ lâu dài

## 📊 Các tính năng chính:
- ✅ Quản lý hàng hóa (CRUD)
- ✅ Nhập hàng với cập nhật tồn kho
- ✅ Xuất bán với kiểm tra tồn kho
- ✅ Báo cáo doanh thu với xuất Excel
- ✅ Tìm kiếm và lọc dữ liệu
- ✅ Giao diện thân thiện với người dùng
