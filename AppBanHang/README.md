# Ứng Dụng Quản Lý Tạp Hóa

Ứng dụng desktop quản lý cửa hàng tạp hóa được xây dựng bằng Java Spring Boot với giao diện Thymeleaf.

## Tính Năng Chính

### 📦 Quản Lý Hàng Hóa
- Thêm, sửa, xóa hàng hóa
- Tìm kiếm theo tên, loại, nhà sản xuất
- Cảnh báo hàng sắp hết
- Theo dõi số lượng tồn kho

### 📥 Quản Lý Nhập Hàng
- Ghi nhận phiếu nhập hàng
- Tự động cập nhật số lượng tồn kho
- Lịch sử nhập hàng chi tiết
- Báo cáo chi phí nhập hàng

### 📤 Quản Lý Xuất Bán
- Ghi nhận giao dịch bán hàng
- Kiểm tra số lượng tồn kho trước khi bán
- Lịch sử bán hàng
- Thống kê sản phẩm bán chạy

### 📊 Báo Cáo Doanh Thu
- Tính toán lợi nhuận tự động
- Tỷ suất lợi nhuận
- Xuất báo cáo Excel
- Biểu đồ doanh thu

## Cấu Trúc Dự Án

```
src/
├── main/
│   ├── java/com/taphoa/appbanhang/
│   │   ├── entity/          # Các entity classes
│   │   │   ├── Hanghoa.java
│   │   │   ├── Nhap.java
│   │   │   ├── XuatBan.java
│   │   │   └── DoanhThu.java
│   │   ├── repository/      # Repository interfaces
│   │   ├── service/         # Business logic
│   │   ├── controller/      # App controllers
│   │   └── AppBanHangApplication.java
│   └── resources/
│       ├── templates/       # Thymeleaf templates
│       │   ├── hanghoa/
│       │   ├── nhap/
│       │   ├── xuatban/
│       │   ├── doanhthu/
│       │   ├── dashboard.html
│       │   └── index.html
│       └── application.properties
└── test/
```

## Yêu Cầu Hệ Thống

- **Java**: 17 hoặc cao hơn
- **Maven**: 3.6+
- **MySQL**: 8.0+
- **Trình duyệt**: Chrome, Firefox, Safari, Edge

## Cài Đặt và Chạy

### 1. Cài Đặt Cơ Sở Dữ Liệu

Tạo database MySQL:
```sql
CREATE DATABASE taphoa_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

### 2. Cấu Hình Database

Cập nhật file `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/taphoa_db
spring.datasource.username=root
spring.datasource.password=your_password
```

### 3. Chạy Ứng Dụng

```bash
# Cách 1: Sử dụng Maven
mvn spring-boot:run

# Cách 2: Build và chạy JAR
mvn clean package
java -jar target/app-ban-hang-0.0.1-SNAPSHOT.jar
```

### 4. Truy Cập Ứng Dụng

Mở trình duyệt và truy cập: `http://localhost:8080`

## Cấu Trúc Database

### Bảng `hanghoa`
- `hanghoa_id` (PK): ID hàng hóa
- `ten_hang_hoa`: Tên hàng hóa
- `so_luong_hang_hoa`: Số lượng tồn kho
- `nha_san_xuat`: Nhà sản xuất
- `nam_san_xuat`: Năm sản xuất
- `loai_hang_hoa`: Loại hàng hóa

### Bảng `nhap`
- `nhap_id` (PK): ID phiếu nhập
- `hanghoa_id` (FK): ID hàng hóa
- `ten_hang_hoa`: Tên hàng hóa
- `so_luong_nhap`: Số lượng nhập
- `gia_nhap`: Giá nhập
- `ngay_nhap`: Ngày nhập

### Bảng `xuatban`
- `xuat_ban_id` (PK): ID phiếu xuất
- `hanghoa_id` (FK): ID hàng hóa
- `ten_hang_hoa`: Tên hàng hóa
- `so_luong_ban`: Số lượng bán
- `gia_ban`: Giá bán
- `ngay_ban`: Ngày bán

### Bảng `doanhthu`
- `doanh_thu_id` (PK): ID báo cáo
- `ty_xuat_loi_nhuan`: Tỷ suất lợi nhuận (%)
- `loi_nhuan`: Lợi nhuận
- `ngay_bao_cao`: Ngày báo cáo
- `tong_doanh_thu`: Tổng doanh thu
- `tong_chi_phi`: Tổng chi phí
- `ghi_chu`: Ghi chú

## API Endpoints

### Hàng Hóa
- `GET /hanghoa` - Danh sách hàng hóa
- `GET /hanghoa/add` - Form thêm hàng hóa
- `POST /hanghoa/add` - Thêm hàng hóa mới
- `GET /hanghoa/edit/{id}` - Form sửa hàng hóa
- `POST /hanghoa/edit/{id}` - Cập nhật hàng hóa
- `POST /hanghoa/delete/{id}` - Xóa hàng hóa
- `GET /hanghoa/detail/{id}` - Chi tiết hàng hóa
- `GET /hanghoa/low-stock` - Hàng sắp hết

### Nhập Hàng
- `GET /nhap` - Danh sách phiếu nhập
- `GET /nhap/add` - Form nhập hàng
- `POST /nhap/add` - Tạo phiếu nhập mới

### Xuất Bán
- `GET /xuatban` - Danh sách phiếu xuất
- `GET /xuatban/add` - Form bán hàng
- `POST /xuatban/add` - Tạo phiếu xuất mới

### Doanh Thu
- `GET /doanhthu` - Báo cáo doanh thu
- `GET /doanhthu/export` - Xuất Excel
- `POST /doanhthu/generate` - Tạo báo cáo

## Công Nghệ Sử Dụng

- **Backend**: Spring Boot 3.2, Spring Data JPA, Spring App Framework
- **Frontend**: Thymeleaf, Bootstrap 5, Font Awesome, Chart.js
- **Database**: MySQL 8.0
- **Build Tool**: Maven
- **Validation**: Spring Boot Validation
- **Excel Export**: Apache POI

## Tính Năng Nổi Bật

### 🔍 Tìm Kiếm Thông Minh
- Tìm kiếm theo nhiều tiêu chí
- Gợi ý tự động
- Lọc theo loại hàng hóa

### 📊 Dashboard Trực Quan
- Biểu đồ doanh thu
- Thống kê realtime
- Cảnh báo hàng sắp hết

### 📱 Responsive Design
- Tối ưu cho mobile
- Giao diện thân thiện
- Trải nghiệm người dùng tốt

### 🔐 Validation
- Kiểm tra dữ liệu đầu vào
- Thông báo lỗi rõ ràng
- Bảo vệ tính toàn vẹn dữ liệu

## Phát Triển

### Chạy ở Development Mode
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

### Chạy Tests
```bash
mvn test
```

### Build Production
```bash
mvn clean package -Pprod
```

## Đóng Góp

1. Fork dự án
2. Tạo feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit changes (`git commit -m 'Add AmazingFeature'`)
4. Push to branch (`git push origin feature/AmazingFeature`)
5. Tạo Pull Request

## Giấy Phép

Dự án này được phân phối dưới giấy phép MIT. Xem file `LICENSE` để biết thêm chi tiết.

## Liên Hệ

- **Developer**: [Tên của bạn]
- **Email**: [email@example.com]
- **GitHub**: [https://github.com/username/AppBanHang]

## Changelog

### Version 1.0.0
- ✅ Quản lý hàng hóa cơ bản
- ✅ Nhập xuất hàng
- ✅ Báo cáo doanh thu
- ✅ Dashboard tổng quan
- ✅ Export Excel
- ✅ Responsive design

### Kế Hoạch Phát Triển
- 🔄 Quản lý người dùng
- 🔄 Báo cáo nâng cao
- 🔄 API REST
- 🔄 Mobile app
- 🔄 Tích hợp thanh toán
