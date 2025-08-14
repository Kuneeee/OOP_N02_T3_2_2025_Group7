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
OOP_N02_T3_2_2025_Group7/
├── README.md             
├── build.gradle           
├── gradle/               
├── gradlew                
├── gradlew.bat          
├── mvnw              
├── mvnw.cmd             
├── pom.xml              
├── review/              
├── settings.gradle
├── cp.txt     
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── ...        # Mã nguồn Java chính (chia theo package: model, controller, service, util, view)
│   │   └── resources/
│   │       └── ...        # File cấu hình, dữ liệu mẫu, template giao diện
│   └── test/
│       ├── java/
│       │   └── ...        # Mã nguồn kiểm thử tự động
│       └── resources/
│           └── ...        # Dữ liệu phục vụ kiểm thử
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
