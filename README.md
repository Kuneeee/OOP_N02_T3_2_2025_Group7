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
## Class Diagram
 
<img width="566" height="724" alt="Diagram" src="https://github.com/user-attachments/assets/36f44e29-11cb-4d35-9a91-011c49589cc4" />

## Activity Diagram-Tạo mặt hàng đồ ăn vặt mới

<img width="354" height="545" alt="Ac1" src="https://github.com/user-attachments/assets/a769f436-a637-4164-80ff-a288f321ce9e" />

## Activity Diagram-Tạo đơn bán hàng mới 

<img width="558" height="655" alt="Ac2" src="https://github.com/user-attachments/assets/f3489009-56da-4e9a-a041-b571ef52a34f" />

## Activity Diagram-Tìm kiếm hàng trong kho hàng hóa

<img width="577" height="655" alt="Ac3" src="https://github.com/user-attachments/assets/14340f1f-b41b-4e2d-85a6-01db76ab90cd" />

## Activity Diagram-Tạo khách hàng mới

<img width="377" height="655" alt="Ac4" src="https://github.com/user-attachments/assets/bff5bcab-178d-42b8-94a3-06c8ca674f74" />


## Activity Diagram-Xóa khách hàng

<img width="386" height="655" alt="Ac5 (1)" src="https://github.com/user-attachments/assets/8852bb83-b6d7-4e6b-a2e0-f9d2861499bb" />

## Giao diện chương trình

<img width="1919" height="946" alt="Screenshot 2025-08-15 014016" src="https://github.com/user-attachments/assets/2b62bdde-a562-487c-819d-3b1c402d0dcf" />

## Thông tin 
Được xây dựng bằng ngôn ngữ lập trình Java và GUI Springboot
## Hướng phát triển tương lai
- Thêm giao diện người dùng: JavaFX hoặc Swing
- Chức năng tìm kiếm theo tên/mã hàng hóa
- Lưu trữ dữ liệu bằng file hoặc tích hợp với database
