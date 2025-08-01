package entity;

/**
 * Entity quản lý nhập hàng
 * Kế thừa từ HangHoa và bổ sung logic nhập hàng
 */
public class Nhap extends HangHoa {
    
    private int soLuongNhap;
    private double giaNhapDonVi;
    private String nhaCungCap;
    private String ghiChu;

    // Constructors
    public Nhap() {
        super();
    }

    public Nhap(String hanghoaID, String tenHangHoa, int soLuongNhap, double giaNhap, String ngayNhap, String loaiHangHoa) {
        super(hanghoaID, tenHangHoa, soLuongNhap, ngayNhap, giaNhap, loaiHangHoa);
        this.soLuongNhap = soLuongNhap;
        this.giaNhapDonVi = giaNhap;
    }

    public Nhap(String hanghoaID, String tenHangHoa, int soLuongNhap, double giaNhapDonVi, 
                String ngayNhap, String loaiHangHoa, String nhaCungCap, String ghiChu) {
        super(hanghoaID, tenHangHoa, soLuongNhap, ngayNhap, giaNhapDonVi, loaiHangHoa);
        this.soLuongNhap = soLuongNhap;
        this.giaNhapDonVi = giaNhapDonVi;
        this.nhaCungCap = nhaCungCap;
        this.ghiChu = ghiChu;
    }

    // Getters và Setters
    public int getSoLuongNhap() { 
        return soLuongNhap; 
    }
    
    public void setSoLuongNhap(int soLuongNhap) { 
        this.soLuongNhap = soLuongNhap;
        // Cập nhật số lượng trong hàng hóa chính
        super.setSoLuongHangHoa(super.getSoLuongHangHoa() + soLuongNhap);
    }

    public double getGiaNhapDonVi() { 
        return giaNhapDonVi; 
    }
    
    public void setGiaNhapDonVi(double giaNhapDonVi) { 
        this.giaNhapDonVi = giaNhapDonVi;
        super.setGiaNhap(giaNhapDonVi);
    }

    public String getNhaCungCap() { 
        return nhaCungCap; 
    }
    
    public void setNhaCungCap(String nhaCungCap) { 
        this.nhaCungCap = nhaCungCap; 
    }

    public String getGhiChu() { 
        return ghiChu; 
    }
    
    public void setGhiChu(String ghiChu) { 
        this.ghiChu = ghiChu; 
    }

    // Business Logic Methods

    /**
     * Tính tổng tiền nhập cho lô hàng này
     */
    public double tinhTongTienNhap() {
        return soLuongNhap * giaNhapDonVi;
    }

    /**
     * Kiểm tra tính hợp lệ của phiếu nhập
     */
    public boolean kiemTraPhieuNhap() {
        if (!validateData()) return false;
        
        if (soLuongNhap <= 0) {
            System.out.println("Lỗi: Số lượng nhập phải lớn hơn 0!");
            return false;
        }
        
        if (giaNhapDonVi <= 0) {
            System.out.println("Lỗi: Giá nhập đơn vị phải lớn hơn 0!");
            return false;
        }
        
        return true;
    }

    /**
     * Thực hiện nhập hàng vào kho
     */
    public boolean thucHienNhapHang() {
        if (!kiemTraPhieuNhap()) {
            return false;
        }
        
        System.out.println("=== THỰC HIỆN NHẬP HÀNG ===");
        System.out.println("Mã hàng hóa: " + getHanghoaID());
        System.out.println("Tên hàng hóa: " + getTenHangHoa());
        System.out.println("Số lượng nhập: " + soLuongNhap);
        System.out.println("Giá nhập đơn vị: " + String.format("%.2f VND", giaNhapDonVi));
        System.out.println("Tổng tiền: " + String.format("%.2f VND", tinhTongTienNhap()));
        System.out.println("Nhà cung cấp: " + (nhaCungCap != null ? nhaCungCap : "Chưa cập nhật"));
        System.out.println("Ngày nhập: " + getNgayNhap());
        System.out.println("Nhập hàng thành công!");
        System.out.println("===============================");
        
        return true;
    }

    /**
     * Hiển thị thông tin phiếu nhập
     */
    @Override
    public void hienThiThongTin() {
        System.out.println("=== PHIẾU NHẬP HÀNG ===");
        System.out.println("Mã hàng hóa: " + getHanghoaID());
        System.out.println("Tên hàng hóa: " + getTenHangHoa());
        System.out.println("Số lượng nhập: " + soLuongNhap);
        System.out.println("Giá nhập đơn vị: " + String.format("%.2f VND", giaNhapDonVi));
        System.out.println("Tổng tiền: " + String.format("%.2f VND", tinhTongTienNhap()));
        System.out.println("Nhà cung cấp: " + (nhaCungCap != null ? nhaCungCap : "Chưa cập nhật"));
        System.out.println("Ngày nhập: " + getNgayNhap());
        System.out.println("Loại hàng hóa: " + getLoaiHangHoa());
        if (ghiChu != null && !ghiChu.trim().isEmpty()) {
            System.out.println("Ghi chú: " + ghiChu);
        }
        System.out.println("=======================");
    }

    /**
     * Cập nhật thông tin nhập hàng
     */
    public void capNhatThongTinNhap(int soLuongMoi, double giaMoi, String nhaCungCapMoi, String ghiChuMoi) {
        this.soLuongNhap = soLuongMoi;
        this.giaNhapDonVi = giaMoi;
        this.nhaCungCap = nhaCungCapMoi;
        this.ghiChu = ghiChuMoi;
        
        // Cập nhật thông tin trong entity cha
        super.setGiaNhap(giaMoi);
        super.setSoLuongHangHoa(soLuongMoi);
        
        System.out.println("Đã cập nhật thông tin nhập hàng cho mã: " + getHanghoaID());
    }

    @Override
    public String toString() {
        return String.format("Nhap{ID='%s', Ten='%s', SoLuongNhap=%d, GiaNhap=%.2f, TongTien=%.2f, NhaCungCap='%s'}", 
                           getHanghoaID(), getTenHangHoa(), soLuongNhap, giaNhapDonVi, tinhTongTienNhap(), nhaCungCap);
    }
}
