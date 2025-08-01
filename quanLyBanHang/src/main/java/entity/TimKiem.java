package entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Entity tìm kiếm hàng hóa và bán hàng
 * Tích hợp logic từ entity gốc
 */
public class TimKiem {

    private List<Ban> danhSachBan;
    private List<HangHoa> danhSachHangHoa;

    // Constructors
    public TimKiem() {
        this.danhSachBan = new ArrayList<>();
        this.danhSachHangHoa = new ArrayList<>();
    }

    public TimKiem(List<Ban> danhSachBan) {
        this.danhSachBan = danhSachBan != null ? danhSachBan : new ArrayList<>();
        this.danhSachHangHoa = new ArrayList<>();
    }

    public TimKiem(List<Ban> danhSachBan, List<HangHoa> danhSachHangHoa) {
        this.danhSachBan = danhSachBan != null ? danhSachBan : new ArrayList<>();
        this.danhSachHangHoa = danhSachHangHoa != null ? danhSachHangHoa : new ArrayList<>();
    }

    // Getters và Setters
    public List<Ban> getDanhSachBan() { return danhSachBan; }
    public void setDanhSachBan(List<Ban> danhSachBan) { this.danhSachBan = danhSachBan; }

    public List<HangHoa> getDanhSachHangHoa() { return danhSachHangHoa; }
    public void setDanhSachHangHoa(List<HangHoa> danhSachHangHoa) { this.danhSachHangHoa = danhSachHangHoa; }

    // Tìm kiếm trong danh sách Ban

    /**
     * Tìm các Ban theo tên hàng hóa (không phân biệt hoa thường)
     */
    public List<Ban> timBanTheoTen(String ten) {
        List<Ban> ketQua = new ArrayList<>();
        if (ten == null || ten.trim().isEmpty()) {
            System.out.println("⚠️ Từ khóa tìm kiếm không được để trống!");
            return ketQua;
        }

        String tuKhoa = ten.toLowerCase().trim();
        for (Ban ban : danhSachBan) {
            if (ban.getTenHangHoa() != null && 
                ban.getTenHangHoa().toLowerCase().contains(tuKhoa)) {
                ketQua.add(ban);
            }
        }
        return ketQua;
    }

    /**
     * Tìm Ban theo mã hàng hóa
     */
    public List<Ban> timBanTheoMa(String ma) {
        List<Ban> ketQua = new ArrayList<>();
        if (ma == null || ma.trim().isEmpty()) {
            return ketQua;
        }

        String maTimKiem = ma.toLowerCase().trim();
        for (Ban ban : danhSachBan) {
            if (ban.getHangHoaID() != null && 
                ban.getHangHoaID().toLowerCase().contains(maTimKiem)) {
                ketQua.add(ban);
            }
        }
        return ketQua;
    }

    /**
     * Tìm Ban theo khách hàng
     */
    public List<Ban> timBanTheoKhachHang(String tenKhachHang) {
        List<Ban> ketQua = new ArrayList<>();
        if (tenKhachHang == null || tenKhachHang.trim().isEmpty()) {
            return ketQua;
        }

        String tuKhoa = tenKhachHang.toLowerCase().trim();
        for (Ban ban : danhSachBan) {
            if (ban.getKhachHang() != null && 
                ban.getKhachHang().toLowerCase().contains(tuKhoa)) {
                ketQua.add(ban);
            }
        }
        return ketQua;
    }

    /**
     * Tìm Ban theo khoảng giá
     */
    public List<Ban> timBanTheoKhoangGia(double giaMin, double giaMax) {
        List<Ban> ketQua = new ArrayList<>();
        for (Ban ban : danhSachBan) {
            double tongTien = ban.tinhTongTienBan();
            if (tongTien >= giaMin && tongTien <= giaMax) {
                ketQua.add(ban);
            }
        }
        return ketQua;
    }

    // Tìm kiếm trong danh sách HangHoa

    /**
     * Tìm HangHoa theo tên
     */
    public List<HangHoa> timHangHoaTheoTen(String ten) {
        List<HangHoa> ketQua = new ArrayList<>();
        if (ten == null || ten.trim().isEmpty()) {
            return ketQua;
        }

        String tuKhoa = ten.toLowerCase().trim();
        for (HangHoa hangHoa : danhSachHangHoa) {
            if (hangHoa.getTenHangHoa() != null && 
                hangHoa.getTenHangHoa().toLowerCase().contains(tuKhoa)) {
                ketQua.add(hangHoa);
            }
        }
        return ketQua;
    }

    /**
     * Tìm HangHoa theo loại
     */
    public List<HangHoa> timHangHoaTheoLoai(String loai) {
        List<HangHoa> ketQua = new ArrayList<>();
        if (loai == null || loai.trim().isEmpty()) {
            return ketQua;
        }

        String tuKhoa = loai.toLowerCase().trim();
        for (HangHoa hangHoa : danhSachHangHoa) {
            if (hangHoa.getLoaiHangHoa() != null && 
                hangHoa.getLoaiHangHoa().toLowerCase().contains(tuKhoa)) {
                ketQua.add(hangHoa);
            }
        }
        return ketQua;
    }

    /**
     * Tìm HangHoa sắp hết (số lượng <= ngưỡng)
     */
    public List<HangHoa> timHangHoaSapHet(int nguongSapHet) {
        List<HangHoa> ketQua = new ArrayList<>();
        for (HangHoa hangHoa : danhSachHangHoa) {
            if (hangHoa.getSoLuongHangHoa() <= nguongSapHet) {
                ketQua.add(hangHoa);
            }
        }
        return ketQua;
    }

    // Tìm kiếm tổng hợp

    /**
     * Tìm kiếm tổng hợp theo từ khóa (tìm trong cả Ban và HangHoa)
     */
    public void timKiemTongHop(String tuKhoa) {
        if (tuKhoa == null || tuKhoa.trim().isEmpty()) {
            System.out.println("⚠️ Vui lòng nhập từ khóa tìm kiếm!");
            return;
        }

        System.out.println("🔍 Tìm kiếm với từ khóa: \"" + tuKhoa + "\"");
        System.out.println("==========================================");

        // Tìm trong danh sách Ban
        List<Ban> ketQuaBan = timBanTheoTen(tuKhoa);
        List<Ban> ketQuaBanTheoMa = timBanTheoMa(tuKhoa);
        
        // Gộp kết quả và loại bỏ trùng lặp
        for (Ban ban : ketQuaBanTheoMa) {
            if (!ketQuaBan.contains(ban)) {
                ketQuaBan.add(ban);
            }
        }

        if (!ketQuaBan.isEmpty()) {
            System.out.println("📋 KẾT QUẢ TÌM TRONG BÁN HÀNG:");
            for (Ban ban : ketQuaBan) {
                System.out.println("  • " + ban.toString());
            }
        }

        // Tìm trong danh sách HangHoa
        List<HangHoa> ketQuaHangHoa = timHangHoaTheoTen(tuKhoa);
        if (!ketQuaHangHoa.isEmpty()) {
            System.out.println("📦 KẾT QUẢ TÌM TRONG HÀNG HÓA:");
            for (HangHoa hangHoa : ketQuaHangHoa) {
                System.out.println("  • " + hangHoa.toString());
            }
        }

        // Thống kê kết quả
        int tongKetQua = ketQuaBan.size() + ketQuaHangHoa.size();
        if (tongKetQua == 0) {
            System.out.println("❌ Không tìm thấy kết quả nào phù hợp!");
        } else {
            System.out.println("==========================================");
            System.out.println("📊 Tổng cộng tìm thấy: " + tongKetQua + " kết quả");
            System.out.println("   - Bán hàng: " + ketQuaBan.size() + " kết quả");
            System.out.println("   - Hàng hóa: " + ketQuaHangHoa.size() + " kết quả");
        }
    }

    /**
     * Tương tác với người dùng để tìm kiếm Ban
     */
    public void timKiemBan(Scanner sc) {
        System.out.println("🔍 === TÌM KIẾM BÁN HÀNG ===");
        System.out.println("1. Tìm theo tên hàng hóa");
        System.out.println("2. Tìm theo mã hàng hóa"); 
        System.out.println("3. Tìm theo khách hàng");
        System.out.println("4. Tìm theo khoảng giá");
        System.out.print("Chọn loại tìm kiếm (1-4): ");
        
        int luaChon = sc.nextInt();
        sc.nextLine(); // consume newline

        switch (luaChon) {
            case 1:
                System.out.print("Nhập tên hàng hóa cần tìm: ");
                String tenHangHoa = sc.nextLine();
                List<Ban> ketQua1 = timBanTheoTen(tenHangHoa);
                hienThiKetQuaTimBan(ketQua1, "tên hàng hóa: " + tenHangHoa);
                break;
                
            case 2:
                System.out.print("Nhập mã hàng hóa cần tìm: ");
                String maHangHoa = sc.nextLine();
                List<Ban> ketQua2 = timBanTheoMa(maHangHoa);
                hienThiKetQuaTimBan(ketQua2, "mã hàng hóa: " + maHangHoa);
                break;
                
            case 3:
                System.out.print("Nhập tên khách hàng cần tìm: ");
                String tenKhachHang = sc.nextLine();
                List<Ban> ketQua3 = timBanTheoKhachHang(tenKhachHang);
                hienThiKetQuaTimBan(ketQua3, "khách hàng: " + tenKhachHang);
                break;
                
            case 4:
                System.out.print("Nhập giá tối thiểu: ");
                double giaMin = sc.nextDouble();
                System.out.print("Nhập giá tối đa: ");
                double giaMax = sc.nextDouble();
                List<Ban> ketQua4 = timBanTheoKhoangGia(giaMin, giaMax);
                hienThiKetQuaTimBan(ketQua4, "khoảng giá: " + giaMin + " - " + giaMax);
                break;
                
            default:
                System.out.println("❌ Lựa chọn không hợp lệ!");
        }
    }

    /**
     * Hiển thị kết quả tìm kiếm Ban
     */
    private void hienThiKetQuaTimBan(List<Ban> ketQua, String dieuKien) {
        System.out.println("==========================================");
        if (ketQua.isEmpty()) {
            System.out.println("❌ Không tìm thấy bán hàng nào phù hợp với " + dieuKien);
        } else {
            System.out.println("✅ Tìm thấy " + ketQua.size() + " kết quả cho " + dieuKien + ":");
            System.out.println("------------------------------------------");
            for (int i = 0; i < ketQua.size(); i++) {
                Ban ban = ketQua.get(i);
                System.out.println((i + 1) + ". " + ban.toString());
                // Có thể hiển thị chi tiết hơn nếu cần
                // ban.hienThiThongTin();
            }
            
            double tongDoanhThu = ketQua.stream().mapToDouble(Ban::tinhTongTienBan).sum();
            System.out.println("------------------------------------------");
            System.out.println("📊 Tổng doanh thu: " + String.format("%.2f VND", tongDoanhThu));
        }
        System.out.println("==========================================");
    }

    /**
     * Tìm kiếm nâng cao với nhiều tiêu chí
     */
    public List<Ban> timKiemNangCao(String tenHangHoa, String khachHang, Double giaMin, Double giaMax) {
        List<Ban> ketQua = new ArrayList<>(danhSachBan);

        // Lọc theo tên hàng hóa
        if (tenHangHoa != null && !tenHangHoa.trim().isEmpty()) {
            String tuKhoa = tenHangHoa.toLowerCase().trim();
            ketQua = ketQua.stream()
                    .filter(ban -> ban.getTenHangHoa() != null && 
                                 ban.getTenHangHoa().toLowerCase().contains(tuKhoa))
                    .collect(Collectors.toList());
        }

        // Lọc theo khách hàng
        if (khachHang != null && !khachHang.trim().isEmpty()) {
            String tuKhoa = khachHang.toLowerCase().trim();
            ketQua = ketQua.stream()
                    .filter(ban -> ban.getKhachHang() != null && 
                                 ban.getKhachHang().toLowerCase().contains(tuKhoa))
                    .collect(Collectors.toList());
        }

        // Lọc theo khoảng giá
        if (giaMin != null) {
            ketQua = ketQua.stream()
                    .filter(ban -> ban.tinhTongTienBan() >= giaMin)
                    .collect(Collectors.toList());
        }

        if (giaMax != null) {
            ketQua = ketQua.stream()
                    .filter(ban -> ban.tinhTongTienBan() <= giaMax)
                    .collect(Collectors.toList());
        }

        return new ArrayList<>(ketQua);
    }

    /**
     * Hiển thị menu tìm kiếm
     */
    public void hienThiMenuTimKiem() {
        System.out.println("🔍 ===== MENU TÌM KIẾM =====");
        System.out.println("1. Tìm kiếm bán hàng");
        System.out.println("2. Tìm kiếm hàng hóa");
        System.out.println("3. Tìm kiếm tổng hợp");
        System.out.println("4. Tìm hàng hóa sắp hết");
        System.out.println("0. Quay lại");
        System.out.println("============================");
    }

    @Override
    public String toString() {
        return String.format("TimKiem{SoBan=%d, SoHangHoa=%d}", 
                           danhSachBan.size(), danhSachHangHoa.size());
    }
}
