package entity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Entity quản lý các hoạt động bán hàng
 * Tích hợp logic từ entity gốc
 */
public class BanManager {
    
    private Long id;
    private String tenManager;
    private String ngayTao;
    
    // Danh sách các đơn bán hàng
    private List<Ban> danhSachBan = new ArrayList<>();

    // Constructors
    public BanManager() {
        this.ngayTao = java.time.LocalDate.now().toString();
        this.tenManager = "Default Manager";
    }

    public BanManager(String tenManager) {
        this();
        this.tenManager = tenManager;
    }

    // Getters và Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTenManager() { return tenManager; }
    public void setTenManager(String tenManager) { this.tenManager = tenManager; }

    public String getNgayTao() { return ngayTao; }
    public void setNgayTao(String ngayTao) { this.ngayTao = ngayTao; }

    public List<Ban> getDanhSachBan() { return danhSachBan; }
    public void setDanhSachBan(List<Ban> danhSachBan) { this.danhSachBan = danhSachBan; }

    // Business Logic Methods

    /**
     * Thêm đơn bán hàng mới
     */
    public void themBan(Ban ban) {
        if (ban != null && ban.kiemTraDonBanHang()) {
            danhSachBan.add(ban);
            System.out.println("✅ Đã thêm đơn bán hàng: " + ban.getHangHoaID());
        } else {
            System.out.println("❌ Không thể thêm đơn bán hàng không hợp lệ!");
        }
    }

    /**
     * Xóa đơn bán hàng theo ID
     */
    public boolean xoaBan(String hangHoaID) {
        boolean removed = danhSachBan.removeIf(ban -> ban.getHangHoaID().equals(hangHoaID));
        if (removed) {
            System.out.println("✅ Đã xóa đơn bán hàng: " + hangHoaID);
        } else {
            System.out.println("❌ Không tìm thấy đơn bán hàng: " + hangHoaID);
        }
        return removed;
    }

    /**
     * Sửa thông tin đơn bán hàng
     */
    public boolean suaBan(String hangHoaID, int soLuongMoi, double giaMoi, String khachHangMoi, String ghiChuMoi) {
        for (Ban ban : danhSachBan) {
            if (ban.getHangHoaID().equals(hangHoaID)) {
                ban.setSoLuongBan(soLuongMoi);
                ban.setGiaBan(giaMoi);
                ban.setKhachHang(khachHangMoi);
                ban.setGhiChu(ghiChuMoi);
                
                System.out.println("✅ Đã cập nhật đơn bán hàng: " + hangHoaID);
                return true;
            }
        }
        System.out.println("❌ Không tìm thấy đơn bán hàng: " + hangHoaID);
        return false;
    }

    /**
     * Tìm đơn bán hàng theo ID
     */
    public Ban timBanTheoID(String hangHoaID) {
        return danhSachBan.stream()
                .filter(ban -> ban.getHangHoaID().equals(hangHoaID))
                .findFirst()
                .orElse(null);
    }

    /**
     * Tìm đơn bán hàng theo tên hàng hóa
     */
    public List<Ban> timBanTheoTen(String tenHangHoa) {
        List<Ban> ketQua = new ArrayList<>();
        for (Ban ban : danhSachBan) {
            if (ban.getTenHangHoa() != null && 
                ban.getTenHangHoa().toLowerCase().contains(tenHangHoa.toLowerCase())) {
                ketQua.add(ban);
            }
        }
        return ketQua;
    }

    /**
     * Tìm đơn bán hàng theo khách hàng
     */
    public List<Ban> timBanTheoKhachHang(String tenKhachHang) {
        List<Ban> ketQua = new ArrayList<>();
        for (Ban ban : danhSachBan) {
            if (ban.getKhachHang() != null && 
                ban.getKhachHang().toLowerCase().contains(tenKhachHang.toLowerCase())) {
                ketQua.add(ban);
            }
        }
        return ketQua;
    }

    /**
     * Hiển thị danh sách tất cả đơn bán hàng
     */
    public void hienThiDanhSach() {
        if (danhSachBan.isEmpty()) {
            System.out.println("📝 Danh sách bán hàng hiện tại trống!");
            return;
        }

        System.out.println("=============================================");
        System.out.println("         📋 DANH SÁCH BÁN HÀNG 📋         ");
        System.out.println("Manager: " + tenManager + " | Ngày tạo: " + ngayTao);
        System.out.println("=============================================");
        
        for (int i = 0; i < danhSachBan.size(); i++) {
            Ban ban = danhSachBan.get(i);
            System.out.println((i + 1) + ". Mã: " + ban.getHangHoaID() + 
                             " | Tên: " + ban.getTenHangHoa() +
                             " | SL: " + ban.getSoLuongBan() +
                             " | Giá: " + String.format("%.0f", ban.getGiaBan()) +
                             " | Tổng: " + String.format("%.0f", ban.tinhTongTienBan()) +
                             " | KH: " + (ban.getKhachHang() != null ? ban.getKhachHang() : "Khách lẻ"));
        }
        System.out.println("=============================================");
        System.out.println("Tổng số đơn hàng: " + danhSachBan.size());
        System.out.println("Tổng doanh thu: " + String.format("%.2f VND", tinhTongDoanhThu()));
        System.out.println("=============================================");
    }

    /**
     * Tính tổng doanh thu từ tất cả đơn bán hàng
     */
    public double tinhTongDoanhThu() {
        return danhSachBan.stream()
                .mapToDouble(Ban::tinhTongTienBan)
                .sum();
    }

    /**
     * Tính tổng số lượng hàng đã bán
     */
    public int tinhTongSoLuongBan() {
        return danhSachBan.stream()
                .mapToInt(Ban::getSoLuongBan)
                .sum();
    }

    /**
     * Tìm đơn hàng có giá trị cao nhất
     */
    public Ban timDonHangGiaTriCaoNhat() {
        return danhSachBan.stream()
                .max((ban1, ban2) -> Double.compare(ban1.tinhTongTienBan(), ban2.tinhTongTienBan()))
                .orElse(null);
    }

    /**
     * Đếm số đơn hàng theo loại khách hàng
     */
    public void thongKeTheoLoaiKhachHang() {
        long donHangVIP = danhSachBan.stream().filter(ban -> ban.tinhTongTienBan() >= 1000000).count();
        long donHangThuong = danhSachBan.size() - donHangVIP;

        System.out.println("=== THỐNG KÊ THEO LOẠI KHÁCH HÀNG ===");
        System.out.println("🌟 Đơn hàng VIP (≥1M): " + donHangVIP);
        System.out.println("👤 Đơn hàng thường: " + donHangThuong);
        System.out.println("📊 Tỷ lệ VIP: " + String.format("%.1f%%", (double)donHangVIP/danhSachBan.size()*100));
        System.out.println("======================================");
    }

    /**
     * Lấy top N đơn hàng có doanh thu cao nhất
     */
    public List<Ban> getTopDoanhThu(int n) {
        return danhSachBan.stream()
                .sorted((ban1, ban2) -> Double.compare(ban2.tinhTongTienBan(), ban1.tinhTongTienBan()))
                .limit(n)
                .collect(Collectors.toList());
    }

    /**
     * Xóa tất cả đơn hàng
     */
    public void xoaTatCaDonHang() {
        int soLuongXoa = danhSachBan.size();
        danhSachBan.clear();
        System.out.println("🗑️ Đã xóa tất cả " + soLuongXoa + " đơn hàng!");
    }

    /**
     * Kiểm tra có đơn hàng nào không
     */
    public boolean coDonHang() {
        return !danhSachBan.isEmpty();
    }

    /**
     * Hiển thị thống kê tổng quan
     */
    public void hienThiThongKeTongQuan() {
        System.out.println("===============================================");
        System.out.println("           📊 THỐNG KÊ TỔNG QUAN 📊           ");
        System.out.println("===============================================");
        System.out.println("Manager: " + tenManager);
        System.out.println("Tổng số đơn hàng: " + danhSachBan.size());
        System.out.println("Tổng doanh thu: " + String.format("%,.2f VND", tinhTongDoanhThu()));
        System.out.println("Tổng số lượng bán: " + tinhTongSoLuongBan() + " sản phẩm");
        
        if (!danhSachBan.isEmpty()) {
            double doanhThuTrungBinh = tinhTongDoanhThu() / danhSachBan.size();
            System.out.println("Doanh thu TB/đơn: " + String.format("%,.2f VND", doanhThuTrungBinh));
            
            Ban donHangLonNhat = timDonHangGiaTriCaoNhat();
            if (donHangLonNhat != null) {
                System.out.println("Đơn hàng lớn nhất: " + donHangLonNhat.getHangHoaID() + 
                                 " (" + String.format("%.0f VND", donHangLonNhat.tinhTongTienBan()) + ")");
            }
        }
        
        System.out.println("===============================================");
        thongKeTheoLoaiKhachHang();
    }

    @Override
    public String toString() {
        return String.format("BanManager{Ten='%s', SoDonHang=%d, TongDoanhThu=%.2f}", 
                           tenManager, danhSachBan.size(), tinhTongDoanhThu());
    }
}
