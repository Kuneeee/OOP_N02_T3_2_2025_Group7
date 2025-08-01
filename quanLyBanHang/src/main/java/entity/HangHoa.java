package entity;

/**
 * Entity chính cho quản lý hàng hóa
 * Tích hợp đầy đủ tính năng từ entity gốc với Spring Boot
 */
public class HangHoa {
    private String hanghoaID;
    private String tenHangHoa;
    private int soLuongHangHoa;
    
    // Thuộc tính bổ sung từ entity gốc
    private String nhaSanXuat;
    private int namSanXuat;
    private String ngayNhap;
    private Double giaNhap;
    private String loaiHangHoa;

    // Constructors
    public HangHoa() {}

    public HangHoa(String hanghoaID, String tenHangHoa, int soLuongHangHoa, String ngayNhap, Double giaNhap, String loaiHangHoa) {
        this.hanghoaID = hanghoaID;
        this.tenHangHoa = tenHangHoa;
        this.soLuongHangHoa = soLuongHangHoa;
        this.ngayNhap = ngayNhap;
        this.giaNhap = giaNhap;
        this.loaiHangHoa = loaiHangHoa;
    }

    // Constructor đầy đủ với tất cả thuộc tính
    public HangHoa(String hanghoaID, String tenHangHoa, int soLuongHangHoa, String nhaSanXuat, 
                   int namSanXuat, String ngayNhap, Double giaNhap, String loaiHangHoa) {
        this.hanghoaID = hanghoaID;
        this.tenHangHoa = tenHangHoa;
        this.soLuongHangHoa = soLuongHangHoa;
        this.nhaSanXuat = nhaSanXuat;
        this.namSanXuat = namSanXuat;
        this.ngayNhap = ngayNhap;
        this.giaNhap = giaNhap;
        this.loaiHangHoa = loaiHangHoa;
    }

    // Getters và Setters
    public String getHanghoaID() { return hanghoaID; }
    public void setHanghoaID(String hanghoaID) { this.hanghoaID = hanghoaID; }
    
    public String getTenHangHoa() { return tenHangHoa; }
    public void setTenHangHoa(String tenHangHoa) { this.tenHangHoa = tenHangHoa; }
    
    public int getSoLuongHangHoa() { return soLuongHangHoa; }
    public void setSoLuongHangHoa(int soLuongHangHoa) { this.soLuongHangHoa = soLuongHangHoa; }
    
    public String getNhaSanXuat() { return nhaSanXuat; }
    public void setNhaSanXuat(String nhaSanXuat) { this.nhaSanXuat = nhaSanXuat; }
    
    public int getNamSanXuat() { return namSanXuat; }
    public void setNamSanXuat(int namSanXuat) { this.namSanXuat = namSanXuat; }
    
    public String getNgayNhap() { return ngayNhap; }
    public void setNgayNhap(String ngayNhap) { this.ngayNhap = ngayNhap; }
    
    public Double getGiaNhap() { return giaNhap; }
    public void setGiaNhap(Double giaNhap) { this.giaNhap = giaNhap; }
    
    public String getLoaiHangHoa() { return loaiHangHoa; }
    public void setLoaiHangHoa(String loaiHangHoa) { this.loaiHangHoa = loaiHangHoa; }

    // Business Logic Methods từ entity gốc
    
    /**
     * Hiển thị thông tin chi tiết hàng hóa
     */
    public void hienThiThongTin() {
        System.out.println("=== THÔNG TIN HÀNG HÓA ===");
        System.out.println("Mã hàng hóa: " + hanghoaID);
        System.out.println("Tên hàng hóa: " + tenHangHoa);
        System.out.println("Số lượng: " + soLuongHangHoa);
        System.out.println("Nhà sản xuất: " + (nhaSanXuat != null ? nhaSanXuat : "Chưa cập nhật"));
        System.out.println("Năm sản xuất: " + (namSanXuat > 0 ? namSanXuat : "Chưa cập nhật"));
        System.out.println("Ngày nhập: " + ngayNhap);
        System.out.println("Giá nhập: " + String.format("%.2f VND", giaNhap));
        System.out.println("Loại hàng hóa: " + loaiHangHoa);
        System.out.println("=============================");
    }

    /**
     * Kiểm tra hàng hóa có hết hạn hay không (dựa trên năm sản xuất)
     */
    public boolean kiemTraHetHan() {
        if (namSanXuat <= 0) return false;
        int namHienTai = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
        int tuoiSanPham = namHienTai - namSanXuat;
        
        // Quy định hết hạn dựa trên loại hàng hóa
        switch (loaiHangHoa != null ? loaiHangHoa.toLowerCase() : "") {
            case "thực phẩm":
            case "đồ ăn vặt":
                return tuoiSanPham > 2; // 2 năm
            case "điện tử":
                return tuoiSanPham > 5; // 5 năm
            case "quần áo":
                return tuoiSanPham > 10; // 10 năm
            default:
                return tuoiSanPham > 3; // Mặc định 3 năm
        }
    }

    /**
     * Tính giá trị tồn kho
     */
    public double tinhGiaTriTonKho() {
        return soLuongHangHoa * (giaNhap != null ? giaNhap : 0.0);
    }

    /**
     * Kiểm tra hàng hóa có sắp hết không
     */
    public boolean kiemTraSapHet() {
        return soLuongHangHoa <= 5; // Ngưỡng cảnh báo: 5 sản phẩm
    }

    /**
     * Cập nhật số lượng hàng hóa (dùng cho nhập/xuất kho)
     */
    public boolean capNhatSoLuong(int soLuongThayDoi) {
        int soLuongMoi = this.soLuongHangHoa + soLuongThayDoi;
        if (soLuongMoi < 0) {
            System.out.println("Lỗi: Không thể giảm số lượng xuống dưới 0!");
            return false;
        }
        this.soLuongHangHoa = soLuongMoi;
        return true;
    }

    /**
     * Validate dữ liệu hàng hóa
     */
    public boolean validateData() {
        if (hanghoaID == null || hanghoaID.trim().isEmpty()) {
            System.out.println("Lỗi: Mã hàng hóa không được để trống!");
            return false;
        }
        if (tenHangHoa == null || tenHangHoa.trim().isEmpty()) {
            System.out.println("Lỗi: Tên hàng hóa không được để trống!");
            return false;
        }
        if (soLuongHangHoa < 0) {
            System.out.println("Lỗi: Số lượng hàng hóa không được âm!");
            return false;
        }
        if (giaNhap != null && giaNhap < 0) {
            System.out.println("Lỗi: Giá nhập không được âm!");
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.format("HangHoa{ID='%s', Ten='%s', SoLuong=%d, LoaiHang='%s', GiaNhap=%.2f}", 
                           hanghoaID, tenHangHoa, soLuongHangHoa, loaiHangHoa, giaNhap);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        HangHoa hangHoa = (HangHoa) obj;
        return hanghoaID != null ? hanghoaID.equals(hangHoa.hanghoaID) : hangHoa.hanghoaID == null;
    }

    @Override
    public int hashCode() {
        return hanghoaID != null ? hanghoaID.hashCode() : 0;
    }
}
