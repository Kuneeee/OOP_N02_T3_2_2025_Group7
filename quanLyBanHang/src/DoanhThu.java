public class DoanhThu {
    // Thuộc tính của doanh thu
    private double tyXuatLoiNhuan;
    private double loiNhuan;
    private double doanhThuThang;
    private double chiPhiNhap;
    
    // Default constructor
    public DoanhThu() {}

    // Constructor với đầy đủ thông tin
    public DoanhThu(double tyXuatLoiNhuan, double loiNhuan, double doanhThuThang, double chiPhiNhap) {
        this.tyXuatLoiNhuan = tyXuatLoiNhuan;
        this.loiNhuan = loiNhuan;
        this.doanhThuThang = doanhThuThang;
        this.chiPhiNhap = chiPhiNhap;
        
        // In thông tin doanh thu chi tiết
        System.out.println("Thông tin doanh thu chi tiết:");
        System.out.println("Doanh thu tháng: " + doanhThuThang + " VND");
        System.out.println("Chi phí nhập: " + chiPhiNhap + " VND");
        System.out.println("Lợi nhuận: " + loiNhuan + " VND");
        System.out.println("Tỷ xuất lợi nhuận: " + tyXuatLoiNhuan + "%");
    }

    // Getter methods
    public double getTyXuatLoiNhuan() { 
        return tyXuatLoiNhuan; 
    }
    
    public double getLoiNhuan() { 
        return loiNhuan; 
    }
    
    public double getDoanhThuThang() { 
        return doanhThuThang; 
    }
    
    public double getChiPhiNhap() { 
        return chiPhiNhap; 
    }

    // Setter methods
    public void setTyXuatLoiNhuan(double tyXuatLoiNhuan) { 
        this.tyXuatLoiNhuan = tyXuatLoiNhuan; 
    }
    
    public void setLoiNhuan(double loiNhuan) { 
        this.loiNhuan = loiNhuan; 
    }
    
    public void setDoanhThuThang(double doanhThuThang) { 
        this.doanhThuThang = doanhThuThang; 
    }
    
    public void setChiPhiNhap(double chiPhiNhap) { 
        this.chiPhiNhap = chiPhiNhap; 
    }

    // Phương thức tính toán doanh thu
    public void tinhDoanhThu(double tongTienBan, double tongTienNhap) {
        this.doanhThuThang = tongTienBan;
        this.chiPhiNhap = tongTienNhap;
        this.loiNhuan = tongTienBan - tongTienNhap;
        
        if (tongTienNhap > 0) {
            this.tyXuatLoiNhuan = (this.loiNhuan / tongTienNhap) * 100;
        } else {
            this.tyXuatLoiNhuan = 0;
        }
        
        System.out.println("Đã tính toán doanh thu:");
        System.out.println("Tổng tiền bán: " + tongTienBan + " VND");
        System.out.println("Tổng tiền nhập: " + tongTienNhap + " VND");
        System.out.println("Lợi nhuận: " + this.loiNhuan + " VND");
        System.out.println("Tỷ xuất lợi nhuận: " + this.tyXuatLoiNhuan + "%");
    }

    // Phương thức kiểm tra hiệu quả kinh doanh
    public String danhGiaHieuQua() {
        if (tyXuatLoiNhuan > 30) {
            return "Kinh doanh rất hiệu quả";
        } else if (tyXuatLoiNhuan > 15) {
            return "Kinh doanh hiệu quả";
        } else if (tyXuatLoiNhuan > 0) {
            return "Kinh doanh có lãi nhưng chưa cao";
        } else {
            return "Kinh doanh thua lỗ";
        }
    }
}
