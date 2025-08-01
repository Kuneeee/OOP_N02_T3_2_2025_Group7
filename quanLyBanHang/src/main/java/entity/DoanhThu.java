package entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Entity quản lý doanh thu và thống kê
 * Tích hợp logic từ entity gốc
 */
public class DoanhThu {
    
    private Long id;
    private double tyXuatLoiNhuan;
    private double loiNhuan;
    private double doanhThuThang;
    private double chiPhiNhap;
    private String ngayTinh;
    private String thangNam;

    // Constructors
    public DoanhThu() {
        this.ngayTinh = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        this.thangNam = LocalDate.now().format(DateTimeFormatter.ofPattern("MM/yyyy"));
    }

    public DoanhThu(double tyXuatLoiNhuan, double loiNhuan, double doanhThuThang, double chiPhiNhap) {
        this();
        this.tyXuatLoiNhuan = tyXuatLoiNhuan;
        this.loiNhuan = loiNhuan;
        this.doanhThuThang = doanhThuThang;
        this.chiPhiNhap = chiPhiNhap;
        
        System.out.println("=== KHỞI TẠO DOANH THU ===");
        System.out.println("Doanh thu tháng: " + String.format("%.2f VND", doanhThuThang));
        System.out.println("Chi phí nhập: " + String.format("%.2f VND", chiPhiNhap));
        System.out.println("Lợi nhuận: " + String.format("%.2f VND", loiNhuan));
        System.out.println("Tỷ xuất lợi nhuận: " + String.format("%.2f%%", tyXuatLoiNhuan));
        System.out.println("========================");
    }

    // Getters và Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public double getTyXuatLoiNhuan() { return tyXuatLoiNhuan; }
    public void setTyXuatLoiNhuan(double tyXuatLoiNhuan) { this.tyXuatLoiNhuan = tyXuatLoiNhuan; }

    public double getLoiNhuan() { return loiNhuan; }
    public void setLoiNhuan(double loiNhuan) { this.loiNhuan = loiNhuan; }

    public double getDoanhThuThang() { return doanhThuThang; }
    public void setDoanhThuThang(double doanhThuThang) { this.doanhThuThang = doanhThuThang; }

    public double getChiPhiNhap() { return chiPhiNhap; }
    public void setChiPhiNhap(double chiPhiNhap) { this.chiPhiNhap = chiPhiNhap; }

    public String getNgayTinh() { return ngayTinh; }
    public void setNgayTinh(String ngayTinh) { this.ngayTinh = ngayTinh; }

    public String getThangNam() { return thangNam; }
    public void setThangNam(String thangNam) { this.thangNam = thangNam; }

    // Business Logic Methods

    /**
     * Tính toán doanh thu từ tổng tiền bán và tổng tiền nhập
     */
    public void tinhDoanhThu(double tongTienBan, double tongTienNhap) {
        this.doanhThuThang = tongTienBan;
        this.chiPhiNhap = tongTienNhap;
        this.loiNhuan = tongTienBan - tongTienNhap;
        
        if (tongTienNhap > 0) {
            this.tyXuatLoiNhuan = (this.loiNhuan / tongTienNhap) * 100;
        } else {
            this.tyXuatLoiNhuan = 0;
        }   
        
        this.ngayTinh = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        
        System.out.println("=== TÍNH TOÁN DOANH THU ===");
        System.out.println("Ngày tính: " + this.ngayTinh);
        System.out.println("Tổng tiền bán: " + String.format("%.2f VND", tongTienBan));
        System.out.println("Tổng tiền nhập: " + String.format("%.2f VND", tongTienNhap));
        System.out.println("Lợi nhuận: " + String.format("%.2f VND", this.loiNhuan));
        System.out.println("Tỷ xuất lợi nhuận: " + String.format("%.2f%%", this.tyXuatLoiNhuan));
        System.out.println("============================");
    }

    /**
     * Đánh giá hiệu quả kinh doanh dựa trên tỷ xuất lợi nhuận
     */
    public String danhGiaHieuQua() {
        if (tyXuatLoiNhuan > 50) {
            return "Kinh doanh RẤT HIỆU QUẢ - Xuất sắc! 🌟";
        } else if (tyXuatLoiNhuan > 30) {
            return "Kinh doanh HIỆU QUẢ - Rất tốt! ⭐";
        } else if (tyXuatLoiNhuan > 15) {
            return "Kinh doanh hiệu quả - Tốt 👍";
        } else if (tyXuatLoiNhuan > 5) {
            return "Kinh doanh có lãi nhưng chưa cao ⚠️";
        } else if (tyXuatLoiNhuan > 0) {
            return "Kinh doanh có lãi ít 😐";
        } else {
            return "Kinh doanh thua lỗ ❌";
        }
    }

    /**
     * Tính lợi nhuận trung bình theo ngày
     */
    public double tinhLoiNhuanTrungBinhTheoNgay() {
        // Giả sử tính theo 30 ngày/tháng
        return loiNhuan / 30;
    }

    /**
     * Dự báo doanh thu tháng tới (dựa trên tăng trưởng)
     */
    public double duBaoDoanhThuThangToi(double tyLeTangTruong) {
        return doanhThuThang * (1 + tyLeTangTruong / 100);
    }

    /**
     * Kiểm tra có đạt mục tiêu lợi nhuận không
     */
    public boolean kiemTraMucTieuLoiNhuan(double mucTieu) {
        return loiNhuan >= mucTieu;
    }

    /**
     * Tính tỷ lệ chi phí trên doanh thu
     */
    public double tinhTyLeChiPhi() {
        if (doanhThuThang == 0) return 0;
        return (chiPhiNhap / doanhThuThang) * 100;
    }

    /**
     * Hiển thị báo cáo doanh thu chi tiết
     */
    public void hienThiBaoCaoDoanhThu() {
        System.out.println("==========================================");
        System.out.println("        📊 BÁO CÁO DOANH THU 📊        ");
        System.out.println("==========================================");
        System.out.println("Thời gian: " + thangNam + " (Ngày tính: " + ngayTinh + ")");
        System.out.println("------------------------------------------");
        System.out.println("💰 Doanh thu tháng: " + String.format("%,.2f VND", doanhThuThang));
        System.out.println("💸 Chi phí nhập hàng: " + String.format("%,.2f VND", chiPhiNhap));
        System.out.println("📈 Lợi nhuận: " + String.format("%,.2f VND", loiNhuan));
        System.out.println("📊 Tỷ xuất lợi nhuận: " + String.format("%.2f%%", tyXuatLoiNhuan));
        System.out.println("📉 Tỷ lệ chi phí: " + String.format("%.2f%%", tinhTyLeChiPhi()));
        System.out.println("💵 Lợi nhuận TB/ngày: " + String.format("%,.2f VND", tinhLoiNhuanTrungBinhTheoNgay()));
        System.out.println("------------------------------------------");
        System.out.println("🔍 Đánh giá: " + danhGiaHieuQua());
        System.out.println("==========================================");
    }

    /**
     * So sánh với doanh thu tháng trước
     */
    public void soSanhDoanhThu(DoanhThu doanhThuThangTruoc) {
        if (doanhThuThangTruoc == null) {
            System.out.println("Không có dữ liệu tháng trước để so sánh.");
            return;
        }

        double tangTruongDoanhThu = ((this.doanhThuThang - doanhThuThangTruoc.doanhThuThang) / doanhThuThangTruoc.doanhThuThang) * 100;
        double tangTruongLoiNhuan = ((this.loiNhuan - doanhThuThangTruoc.loiNhuan) / Math.abs(doanhThuThangTruoc.loiNhuan)) * 100;

        System.out.println("=== SO SÁNH VỚI THÁNG TRƯỚC ===");
        System.out.println("Tăng trưởng doanh thu: " + String.format("%.2f%%", tangTruongDoanhThu));
        System.out.println("Tăng trưởng lợi nhuận: " + String.format("%.2f%%", tangTruongLoiNhuan));
        
        if (tangTruongDoanhThu > 0) {
            System.out.println("📈 Doanh thu tăng - Tích cực!");
        } else {
            System.out.println("📉 Doanh thu giảm - Cần cải thiện!");
        }
        
        System.out.println("=================================");
    }

    /**
     * Xuất báo cáo Excel (giả lập)
     */
    public String xuatBaoCaoExcel() {
        String tenFile = "BaoCaoDoanhThu_" + thangNam.replace("/", "_") + ".xlsx";
        
        // Trong thực tế sẽ sử dụng Apache POI để tạo file Excel
        System.out.println("=== XUẤT BÁO CÁO EXCEL ===");
        System.out.println("Đang tạo file: " + tenFile);
        System.out.println("Nội dung báo cáo:");
        System.out.println("- Doanh thu: " + String.format("%.2f", doanhThuThang));
        System.out.println("- Chi phí: " + String.format("%.2f", chiPhiNhap));
        System.out.println("- Lợi nhuận: " + String.format("%.2f", loiNhuan));
        System.out.println("- Tỷ xuất LN: " + String.format("%.2f%%", tyXuatLoiNhuan));
        System.out.println("Xuất file thành công: " + tenFile);
        System.out.println("===========================");
        
        return tenFile;
    }

    @Override
    public String toString() {
        return String.format("DoanhThu{Thang='%s', DoanhThu=%.2f, LoiNhuan=%.2f, TyXuat=%.2f%%}", 
                           thangNam, doanhThuThang, loiNhuan, tyXuatLoiNhuan);
    }
}
