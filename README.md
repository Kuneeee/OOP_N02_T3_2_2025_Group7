# OOP_N02_T3_2_2025_K18_Group3
# 📋 GIỚI THIỆU

> **Ứng dụng quản lý bán hàng đồ ăn vặt dành cho Đại học Phenikaa.**
>
> Được phát triển bằng Java, hỗ trợ tính năng quản lý kho, nhập hàng, xuất bán và quản lý khách hàng.

## 👨‍💻 Thành viên nhóm
| STT | Họ tên                | Mã SV      | GitHub ID   |
|-----|-----------------------|------------|-------------|
| 1   | Đặng Đức Long         | 24100068   | [Kuneeee](https://github.com/Kuneeee)  |
| 2   | Nguyễn Gia Bảo        | 24100074   | [NgGiaBaoo](https://github.com/NgGiaBaoo)  |
| 3   | Trần Quang Trường     | 24100073   | [tuonie21](https://github.com/tuonie21)  |
## 🎯 Chức năng chính

- 📦 Quản lý hàng hóa (CRUD, tìm kiếm, cập nhật kho)
- 📥 Quản lý nhập hàng (CRUD, kiểm tra trùng lặp, trạng thái nhập)
- 🛒 Quản lý bán hàng (CRUD, tính doanh thu, trạng thái đơn hàng)
- 👤 Quản lý khách hàng (CRUD, điểm thưởng, tổng chi tiêu)
- 📊 Báo cáo doanh thu, xuất file Excel
- Xử lý lỗi hệ thống, giao diện thông báo lỗi (404, 500, 403, ...)

---

## 🏗️ Cấu trúc thư mục

```
quanLyBanHang/
├── build.gradle, pom.xml, gradlew, mvnw, ...
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── controller/   # Controller cho từng entity (HangHoa, Nhap, Ban, KhachHang, Error)
│   │   │   ├── entity/       # Entity: HangHoa, Nhap, Ban, KhachHang
│   │   │   ├── service/      # Service xử lý nghiệp vụ, CRUD, mã tự động
│   │   └── resources/
│   │       ├── templates/    # Giao diện Thymeleaf: index, dashboard, hanghoa, nhap, ban, customers, error
│   │       └── ...           # File cấu hình, dữ liệu mẫu
│   └── test/
│       └── ...               # Kiểm thử tự động
├── review/                   # Các file Java mẫu, thử nghiệm
└── README.md                 # Tài liệu dự án
```

---
## 🏛️ Phân tích đối tượng & Kiến trúc ứng dụng

Ứng dụng được xây dựng theo mô hình hướng đối tượng với các lớp chính sau:

### 1. DoAnVat
- **Mô tả:** Đại diện cho từng mặt hàng đồ ăn vặt được quản lý trong hệ thống.
- **Thuộc tính:**  
  - `maHang` (String): Mã định danh hàng hóa  
  - `tenHang` (String): Tên hàng  
  - `soLuong` (int): Số lượng hiện có  
  - `ngayTao` (Date): Ngay tạo danh sách đồ ăn vặt

### 2. Nhap
- **Mô tả:** Quản lý các phiếu nhập hàng vào kho.
- **Thuộc tính:**  
  - `maNhap` (String): Mã phiếu nhập  
  - `maHang` (String): Mã hàng nhập  
  - `tenHang` (String): Tên hàng nhập  
  - `soLuongNhap` (int): Số lượng nhập  
  - `giaNhap` (double): Giá nhập mỗi đơn vị  
  - `ngayNhap` (Date): Ngày nhập  

### 3. XuatBan
- **Mô tả:** Quản lý các phiếu xuất bán hàng hóa cho khách.
- **Thuộc tính:**  
  - `maXuat` (String): Mã phiếu xuất  
  - `maHang` (String): Mã hàng xuất  
  - `tenHang` (String): Tên hàng bán  
  - `soLuongBan` (int): Số lượng bán  
  - `giaBan` (double): Giá bán mỗi đơn vị  
  - `ngayBan` (Date): Ngày bán  

### 4. KhachHang
- **Mô tả:** Lưu trữ thông tin khách hàng mua hàng tại cửa hàng.
- **Thuộc tính:**  
  - `maKH` (String): Mã khách hàng  
  - `tenKH` (String): Tên khách hàng  
  - `soDienThoai` (String): Số điện thoại liên hệ  
  - `ngayMua` (Date): Ngày mua hàng
  - `emailKH` (String): email khách hàng
## Sơ đồ lớp
 Truy cập sơ đồ phân tích hướng đối tượng tại: https://online.visual-paradigm.com/share.jsp?id=343136313439372d31

## Hướng phát triển tương lai
- Thêm giao diện người dùng: JavaFX hoặc Swing
- Chức năng tìm kiếm theo tên/mã hàng hóa
- Lưu trữ dữ liệu bằng file hoặc tích hợp với database
