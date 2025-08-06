package entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Entity class đại diện cho thông tin nhập hàng
 * Tương thích với NhapController và tránh xung đột với HangHoaController, BanController
 */
public class Nhap {
    // Thuộc tính chính - theo NhapController
    private String hanghoaID;        // ID hàng hóa - khớp với NhapController
    private String tenHangHoa;       // Tên hàng hóa - khớp với NhapController  
    private int soLuongNhap;        // Số lượng nhập - khớp với NhapController
    private double giaNhap;          // Giá nhập - khớp với NhapController
    private String ngayNhap;         // Ngày nhập - khớp với NhapController
    
    // Thuộc tính bổ sung cho nhập hàng
    private String nhaCungCap;       // Nhà cung cấp
    private String nguoiNhap;        // Người thực hiện nhập
    private String ghiChu;           // Ghi chú
    private String trangThai;        // Trạng thái nhập
    private String maNhapHang;       // Mã phiếu nhập (khác với hanghoaID)
    
    // Constructor không tham số
    public Nhap() {
        this.ngayNhap = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.trangThai = "Đã nhập";
        this.ghiChu = "";
        this.nguoiNhap = "";
        this.nhaCungCap = "";
        this.maNhapHang = "";
    }
    
    
    // Constructor đầy đủ cho form
    public Nhap(String hanghoaID, String tenHangHoa, int soLuongNhap, double giaNhap, 
                String ngayNhap, String nhaCungCap, String nguoiNhap, String ghiChu) {
        this.hanghoaID = hanghoaID;
        this.tenHangHoa = tenHangHoa;
        this.soLuongNhap = soLuongNhap;
        this.giaNhap = giaNhap;
        this.ngayNhap = ngayNhap;
        this.nhaCungCap = nhaCungCap;
        this.nguoiNhap = nguoiNhap;
        this.ghiChu = ghiChu;
        this.trangThai = "Đã nhập";
        this.maNhapHang = "NH" + hanghoaID;
    }
    
    // Constructor 6 tham số
    public Nhap(String hanghoaID, String tenHangHoa, int soLuongNhap, double giaNhap, 
                String ngayNhap, String nhaCungCap) {
        this.hanghoaID = hanghoaID;
        this.tenHangHoa = tenHangHoa;
        this.soLuongNhap = soLuongNhap;
        this.giaNhap = giaNhap;
        this.ngayNhap = ngayNhap;
        this.nhaCungCap = nhaCungCap;
        this.trangThai = "Đã nhập";
        this.nguoiNhap = "";
        this.ghiChu = "";
        this.maNhapHang = "NH" + hanghoaID;
    }
    
    // Constructor legacy - tương thích với lỗi Maven ban đầu
    public Nhap(String maNhap, String maSanPham, int soLuong, int donGia, String nhaCungCap) {
        this.maNhapHang = maNhap;
        this.hanghoaID = maSanPham;
        this.tenHangHoa = maSanPham;
        this.soLuongNhap = soLuong;
        this.giaNhap = (double) donGia;
        this.nhaCungCap = nhaCungCap;
        this.ngayNhap = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.trangThai = "Đã nhập";
        this.nguoiNhap = "";
        this.ghiChu = "";
    }
    
    // Getter và Setter methods - Theo đúng NhapController
    public String getHanghoaID() {
        return hanghoaID;
    }
    
    public void setHanghoaID(String hanghoaID) {
        this.hanghoaID = hanghoaID;
        // Tự động cập nhật mã nhập hàng
        if (this.maNhapHang == null || this.maNhapHang.isEmpty()) {
            this.maNhapHang = "NH" + hanghoaID;
        }
    }
    
    public String getTenHangHoa() {
        return tenHangHoa;
    }
    
    public void setTenHangHoa(String tenHangHoa) {
        this.tenHangHoa = tenHangHoa;
    }
    
    public int getSoLuongNhap() {
        return soLuongNhap;
    }
    
    public void setSoLuongNhap(int soLuongNhap) {
        this.soLuongNhap = soLuongNhap;
    }
    
    public double getGiaNhap() {
        return giaNhap;
    }
    
    public void setGiaNhap(double giaNhap) {
        this.giaNhap = giaNhap;
    }
    
    public String getNgayNhap() {
        return ngayNhap;
    }
    
    public void setNgayNhap(String ngayNhap) {
        this.ngayNhap = ngayNhap;
    }
    
    public String getNhaCungCap() {
        return nhaCungCap;
    }
    
    public void setNhaCungCap(String nhaCungCap) {
        this.nhaCungCap = nhaCungCap;
    }
    
    public String getNguoiNhap() {
        return nguoiNhap;
    }
    
    public void setNguoiNhap(String nguoiNhap) {
        this.nguoiNhap = nguoiNhap;
    }
    
    public String getGhiChu() {
        return ghiChu;
    }
    
    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }
    
    public String getTrangThai() {
        return trangThai;
    }
    
    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
    
    public String getMaNhapHang() {
        return maNhapHang;
    }
    
    public void setMaNhapHang(String maNhapHang) {
        this.maNhapHang = maNhapHang;
    }
    
    // Phương thức tính toán - Sử dụng trong Controller
    public double getTongTienNhap() {
        return soLuongNhap * giaNhap;
    }
    
    // Alias cho tính tổng chi phí
    public double getTotalCost() {
        return getTongTienNhap();
    }
    
    // Phương thức kiểm tra hợp lệ
    public boolean isValid() {
        return hanghoaID != null && !hanghoaID.trim().isEmpty() &&
               tenHangHoa != null && !tenHangHoa.trim().isEmpty() &&
               soLuongNhap > 0 && giaNhap > 0 &&
               ngayNhap != null && !ngayNhap.trim().isEmpty();
    }
    
    // Phương thức format giá tiền (cho hiển thị)
    public String getFormattedGiaNhap() {
        return String.format("%,.0f VND", giaNhap);
    }
    
    // Phương thức format tổng tiền (cho hiển thị)
    public String getFormattedTongTien() {
        return String.format("%,.0f VND", getTongTienNhap());
    }
    
    // Phương thức kiểm tra ngày nhập gần đây (trong 30 ngày)
    public boolean isRecentImport() {
        try {
            LocalDate importDate = LocalDate.parse(ngayNhap);
            LocalDate thirtyDaysAgo = LocalDate.now().minusDays(30);
            return importDate.isAfter(thirtyDaysAgo);
        } catch (Exception e) {
            return false;
        }
    }
    
    // Phương thức so sánh với hàng hóa khác (để tránh trùng lặp)
    public boolean isDuplicateWith(String otherHanghoaID, String otherNgayNhap) {
        return this.hanghoaID != null && this.hanghoaID.equals(otherHanghoaID) &&
               this.ngayNhap != null && this.ngayNhap.equals(otherNgayNhap);
    }
    
    // Override toString method
    @Override
    public String toString() {
        return "Nhap{" +
                "maNhapHang='" + maNhapHang + '\'' +
                ", hanghoaID='" + hanghoaID + '\'' +
                ", tenHangHoa='" + tenHangHoa + '\'' +
                ", soLuongNhap=" + soLuongNhap +
                ", giaNhap=" + giaNhap +
                ", ngayNhap='" + ngayNhap + '\'' +
                ", nhaCungCap='" + nhaCungCap + '\'' +
                ", nguoiNhap='" + nguoiNhap + '\'' +
                ", trangThai='" + trangThai + '\'' +
                ", tongTienNhap=" + getTongTienNhap() +
                '}';
    }
    
    // Override equals method - Sử dụng maNhapHang làm key chính
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        Nhap nhap = (Nhap) obj;
        
        // Ưu tiên so sánh bằng maNhapHang
        if (maNhapHang != null && nhap.maNhapHang != null) {
            return maNhapHang.equals(nhap.maNhapHang);
        }
        
        // Fallback: so sánh bằng hanghoaID
        return hanghoaID != null ? hanghoaID.equals(nhap.hanghoaID) : nhap.hanghoaID == null;
    }
    
    // Override hashCode method
    @Override
    public int hashCode() {
        if (maNhapHang != null) {
            return maNhapHang.hashCode();
        }
        return hanghoaID != null ? hanghoaID.hashCode() : 0;
    }
    
    // Phương thức so sánh theo ngày (cho sắp xếp)
    public int compareByDate(Nhap other) {
        if (this.ngayNhap == null || other.ngayNhap == null) {
            return 0;
        }
        return this.ngayNhap.compareTo(other.ngayNhap);
    }
    
    // Phương thức so sánh theo giá (cho sắp xếp)
    public int compareByPrice(Nhap other) {
        return Double.compare(this.giaNhap, other.giaNhap);
    }
    
    // Phương thức so sánh theo số lượng (cho sắp xếp)
    public int compareByQuantity(Nhap other) {
        return Integer.compare(this.soLuongNhap, other.soLuongNhap);
    }
}