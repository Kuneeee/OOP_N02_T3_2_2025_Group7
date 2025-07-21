public class Nhap extends HangHoa {
    // Thuộc tính của hàng hóa nhập
    private String hanghoaID;
    private String tenHangHoa;
    private int soLuongNhap;
    private double giaNhap;
    private String ngayNhap;
    private String loaiHangHoa;
   
    // Default constructor
    public Nhap() {}
   
    // Parameterized constructor
    public Nhap(String hanghoaID, String tenHangHoa, int soLuongNhap, double giaNhap, String ngayNhap, String loaiHangHoa) {
        // Khởi tạo các thuộc tính của hàng hóa nhập
        this.hanghoaID = hanghoaID;
        this.tenHangHoa = tenHangHoa;
        this.soLuongNhap = soLuongNhap;
        this.giaNhap = giaNhap;
        this.ngayNhap = ngayNhap;
        this.loaiHangHoa = loaiHangHoa;
    }

    // Getter methods
    public String getHanghoaID() { 
        return hanghoaID; 
    }
    
    public String getTenHangHoa() { 
        return tenHangHoa; 
    }
    
    public int getSoLuongNhap() { 
        return soLuongNhap; 
    }

    public Double getGiaNhap() { 
        return giaNhap; 
    }
    
    public String getNgayNhap() { 
        return ngayNhap; 
    }

    public String getLoaiHangHoa() { 
        return loaiHangHoa; 
    }

    // Setter methods
    public void setHanghoaID(String hanghoaID) { 
        this.hanghoaID = hanghoaID; 
    }
    
    public void setTenHangHoa(String tenHangHoa) { 
        this.tenHangHoa = tenHangHoa; 
    }
    
    public void setSoLuongNhap(int soLuongNhap) { 
        this.soLuongNhap = soLuongNhap; 
    }
    
    public void setGiaNhap(double giaNhap) { 
        this.giaNhap = giaNhap; 
    }
    
    public void setNgayNhap(String ngayNhap) { 
        this.ngayNhap = ngayNhap; 
    }

    public void setLoaiHangHoa(String loaiHangHoa) { 
        this.loaiHangHoa = loaiHangHoa; 
    }
}