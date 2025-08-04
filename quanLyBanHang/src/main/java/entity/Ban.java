package entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Ban {
    private String banID;
    private String hangHoaID;
    private String tenHangHoa;
    private String tenKhachHang;
    private String khachHang;
    private String ngayBan;
    private String ghiChu;
    private double giaBan;
    private int soLuongBan;
    private double tongTien;
    private double giaNhap;

    public Ban() {}

    public Ban(String banID, String hangHoaID, String tenHangHoa, String tenKhachHang, String ngayBan, double giaBan, int soLuongBan) {
        this.banID = banID;
        this.hangHoaID = hangHoaID;
        this.tenHangHoa = tenHangHoa;
        this.tenKhachHang = tenKhachHang;
        this.khachHang = tenKhachHang;
        this.ngayBan = ngayBan;
        this.giaBan = giaBan;
        this.soLuongBan = soLuongBan;
        this.tongTien = giaBan * soLuongBan;
        this.ghiChu = "";
    }

    public Ban(String banID, String hangHoaID, String tenHangHoa, String khachHang, String ngayBan, double giaBan, int soLuongBan, String ghiChu) {
        this.banID = banID;
        this.hangHoaID = hangHoaID;
        this.tenHangHoa = tenHangHoa;
        this.khachHang = khachHang;
        this.tenKhachHang = khachHang;
        this.ngayBan = ngayBan;
        this.giaBan = giaBan;
        this.soLuongBan = soLuongBan;
        this.tongTien = giaBan * soLuongBan;
        this.ghiChu = ghiChu;
    }

    // Getters and Setters
    public String getBanID() { return banID; }
    public void setBanID(String banID) { this.banID = banID; }
    public String getHangHoaID() { return hangHoaID; }
    public void setHangHoaID(String hangHoaID) { this.hangHoaID = hangHoaID; }
    public String getTenHangHoa() { return tenHangHoa; }
    public void setTenHangHoa(String tenHangHoa) { this.tenHangHoa = tenHangHoa; }
    public String getTenKhachHang() { return tenKhachHang; }
    public void setTenKhachHang(String tenKhachHang) { 
        this.tenKhachHang = tenKhachHang;
        this.khachHang = tenKhachHang;
    }
    public String getKhachHang() { return khachHang; }
    public void setKhachHang(String khachHang) { 
        this.khachHang = khachHang;
        this.tenKhachHang = khachHang;
    }
    public String getNgayBan() { return ngayBan; }
    public void setNgayBan(String ngayBan) { this.ngayBan = ngayBan; }
    public String getGhiChu() { return ghiChu; }
    public void setGhiChu(String ghiChu) { this.ghiChu = ghiChu; }
    public double getGiaBan() { return giaBan; }
    public void setGiaBan(double giaBan) { this.giaBan = giaBan; }
    public int getSoLuongBan() { return soLuongBan; }
    public void setSoLuongBan(int soLuongBan) { this.soLuongBan = soLuongBan; }
    public double getTongTien() { return tongTien; }
    public void setTongTien(double tongTien) { this.tongTien = tongTien; }
    public double getGiaNhap() { return giaNhap; }
    public void setGiaNhap(double giaNhap) { this.giaNhap = giaNhap; }

    // Business Logic Methods
    public double tinhTongTienBan() {
        this.tongTien = this.giaBan * this.soLuongBan;
        return this.tongTien;
    }

    public double tinhLoiNhuan() {
        if (this.giaNhap > 0) {
            return (this.giaBan - this.giaNhap) * this.soLuongBan;
        }
        return 0;
    }

    public boolean kiemTraDonBanHang() {
        // Kiểm tra tính hợp lệ của đơn bán hàng
        if (this.banID == null || this.banID.trim().isEmpty()) {
            return false;
        }
        if (this.hangHoaID == null || this.hangHoaID.trim().isEmpty()) {
            return false;
        }
        if (this.khachHang == null || this.khachHang.trim().isEmpty()) {
            return false;
        }
        if (this.soLuongBan <= 0) {
            return false;
        }
        if (this.giaBan <= 0) {
            return false;
        }
        if (this.ngayBan == null || this.ngayBan.trim().isEmpty()) {
            return false;
        }
        return true;
    }

    public boolean kiemTraNgayBan() {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate ngay = LocalDate.parse(this.ngayBan, formatter);
            LocalDate hienTai = LocalDate.now();
            return !ngay.isAfter(hienTai);
        } catch (Exception e) {
            return false;
        }
    }

    public String layThongTinBan() {
        return String.format("ID: %s | Hàng hóa: %s | Khách hàng: %s | Ngày: %s | Số lượng: %d | Giá: %.2f | Tổng tiền: %.2f",
                this.banID, this.tenHangHoa, this.khachHang, this.ngayBan, this.soLuongBan, this.giaBan, this.tongTien);
    }

    @Override
    public String toString() {
        return "Ban{" +
                "banID='" + banID + '\'' +
                ", hangHoaID='" + hangHoaID + '\'' +
                ", tenHangHoa='" + tenHangHoa + '\'' +
                ", khachHang='" + khachHang + '\'' +
                ", ngayBan='" + ngayBan + '\'' +
                ", giaBan=" + giaBan +
                ", soLuongBan=" + soLuongBan +
                ", tongTien=" + tongTien +
                ", ghiChu='" + ghiChu + '\'' +
                '}';
    }
}
