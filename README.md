# OOP_N02_T3_2_2025_K18_Group3
# 📋 GIỚI THIỆU

> **Ứng dụng quản lý bán hàng đồ ăn vặt dành cho Đại học Phenikaa.**
>
> Được phát triển bằng Java, hỗ trợ tính năng quản lý kho, nhập hàng, xuất bán và quản lý khách hàng.

---

## 🎯 Chức năng chính
- 📦 Quản lý hàng hóa  
- 📥 Quản lý nhập & xuất bán hàng hóa  
- 👤 Quản lý khách hàng mua hàng  
- 📊 Tính toán doanh thu, xuất báo cáo Excel

---

## 👨‍💻 Thành viên nhóm
| STT | Họ tên                | Mã SV      | GitHub ID   |
|-----|-----------------------|------------|-------------|
| 1   | Đặng Đức Long         | 24100068   | [Kuneeee](https://github.com/Kuneeee)  |
| 2   | Nguyễn Gia Bảo        | 24100074   | [NgGiaBaoo](https://github.com/NgGiaBaoo)  |
| 3   | Trần Quang Trường     | 24100073   | [tuonie21](https://github.com/tuonie21)  |
## 🏗️ Cấu trúc thư mục chính

```
quanLyBanHang/
├── build.gradle         # File cấu hình cho Gradle (quản lý dependencies, tasks)
├── cp.txt               # File thông tin bổ sung (ví dụ: đường dẫn classpath)
├── gradle/              # Thư mục hệ thống của Gradle
├── gradlew              # Script chạy Gradle trên Linux/Mac
├── gradlew.bat          # Script chạy Gradle trên Windows
├── mvnw                 # Script chạy Maven trên Linux/Mac
├── mvnw.cmd             # Script chạy Maven trên Windows
├── pom.xml              # File cấu hình cho Maven (quản lý dependencies, plugins)
├── review/              # Thư mục chứa tài liệu review hoặc báo cáo
├── settings.gradle      # File cấu hình dự án cho Gradle
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── group3/
│   │   │           └── quanlybanhang/
│   │   │               ├── App.java                  # Lớp khởi động ứng dụng
│   │   │               ├── model/
│   │   │               │   ├── HangHoa.java          # Định nghĩa đối tượng Hàng hóa
│   │   │               │   ├── KhachHang.java        # Định nghĩa đối tượng Khách hàng
│   │   │               │   ├── Nhap.java             # Định nghĩa nhập hàng
│   │   │               │   ├── XuatBan.java          # Định nghĩa xuất bán hàng
│   │   │               │   └── DoanhThu.java         # Định nghĩa doanh thu
│   │   │               ├── controller/
│   │   │               │   ├── HangHoaController.java
│   │   │               │   └── KhachHangController.java
│   │   │               ├── service/
│   │   │               │   ├── HangHoaService.java
│   │   │               │   └── DoanhThuService.java
│   │   │               ├── util/
│   │   │               │   ├── ExcelExporter.java    # Xuất báo cáo ra file Excel
│   │   │               │   └── Validator.java        # Kiểm tra dữ liệu đầu vào
│   │   │               └── view/
│   │   │                   ├── MainUI.java           # Giao diện chính (nếu có)
│   │   │                   └── ...
│   │   └── resources/
│   │       ├── config.properties        # File cấu hình ứng dụng
│   │       ├── data/
│   │       │   ├── hanghoa.csv          # Dữ liệu mẫu hàng hóa
│   │       │   └── khachhang.csv        # Dữ liệu mẫu khách hàng
│   │       └── templates/               # Template giao diện nếu dùng JavaFX hoặc Swing
│   └── test/
│       ├── java/
│       │   └── com/
│       │       └── group3/
│       │           └── quanlybanhang/
│       │               ├── model/
│       │               │   ├── HangHoaTest.java
│       │               │   └── KhachHangTest.java
│       │               ├── service/
│       │               │   └── HangHoaServiceTest.java
│       │               └── ...
│       └── resources/
│           └── test-data/
│               ├── hanghoa-test.csv
│               └── khachhang-test.csv
```
## Phân tích đối tượng

Hanghoa
```
+ hanghoaID
+ tenHangHoa
+ soLuongHangHoa
+ nhaSanXuat
+ namSanXuat
+ loaiHangHoa

```

Nhap
```
+ hanghoaID
+ tenHangHoa
+ soLuongNhap
+ giaNhap
+ ngayNhap

```
XuatBan
```
+ hangHoaID
+ tenHangHoa
+ soLuongBan
+ giaBan

```
Khách Hàng
```
```
## Mục tiêu
 Phát triển một ứng dụng giúp quản lý hàng hóa, nhập – bán – tính doanh thu một cách trực quan và hiệu quả cho cửa hàng tạp hóa.

## Kiến trúc ứng dụng
 Ứng dụng được xây dựng dựa trên Java, chia thành các đối tượng hướng đối tượng:

 📦 HangHoa
- ID sản phẩm, tên, số lượng tồn kho, nhà sản xuất, năm sản xuất, loại hàng hóa

 📝 Nhap
- Nhập hàng mới: mã sản phẩm, số lượng, giá nhập, ngày nhập

 🛒 XuatBan
- Bán hàng: mã sản phẩm, số lượng bán, giá bán

 📈 DoanhThu
- Tính toán lợi nhuận, tỷ suất lợi nhuận, xuất báo cáo Excel

## Tính năng chính
- Quản lý hiển thị danh sách hàng hóa, số lượng tồn
- Nhập hàng và cập nhật kho tự động
- Bán hàng và trừ kho
- Tính toán doanh thu, lợi nhuận theo từng đợt
- Xuất báo cáo Excel bằng Apache POI

## Sơ đồ lớp
 Truy cập sơ đồ phân tích hướng đối tượng tại: https://online.visual-paradigm.com/share.jsp?id=343136313439372d31

## Hướng phát triển tương lai
- Thêm giao diện người dùng: JavaFX hoặc Swing
- Chức năng tìm kiếm theo tên/mã hàng hóa
- Lưu trữ dữ liệu bằng file hoặc tích hợp với database
