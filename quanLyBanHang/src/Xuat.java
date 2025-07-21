public class Xuat {
    // Thuộc tính của hàng hóa xuất
    private String hanghoaID;
    private String tenHangHoa;
    private int soLuongXuat;
    private double giaXuat;
    private String ngayXuat;
    private String loaiHangHoa;
    
    // Default constructor
    public Xuat() {}

    // Parameterized constructor
    public Xuat(String hanghoaID, String tenHangHoa, int soLuongXuat, double giaXuat, String ngayXuat, String loaiHangHoa) {
        // Khởi tạo các thuộc tính của hàng hóa xuất
        this.hanghoaID = hanghoaID;
        this.tenHangHoa = tenHangHoa;
        this.soLuongXuat = soLuongXuat;
        this.giaXuat = giaXuat;
        this.ngayXuat = ngayXuat;
        this.loaiHangHoa = loaiHangHoa;

        // In thông tin hàng hóa xuất
        System.out.println("Thông tin xuất hàng:");
        System.out.println("ID hàng hóa: " + hanghoaID);
        System.out.println("Tên hàng hóa: " + tenHangHoa);
        System.out.println("Số lượng xuất: " + soLuongXuat);
        System.out.println("Giá xuất: " + giaXuat);
        System.out.println("Ngày xuất: " + ngayXuat);
        System.out.println("Loại hàng hóa: " + loaiHangHoa);
    }
   
    // Getter methods
    public String getHanghoaID() { 
        return hanghoaID; 
    }
    
    public String getTenHangHoa() { 
        return tenHangHoa; 
    }
    
    public int getSoLuongXuat() { 
        return soLuongXuat; 
    }
    
    public double getGiaXuat() { 
        return giaXuat; 
    }
    
    public String getNgayXuat() { 
        return ngayXuat; 
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
    
    public void setSoLuongXuat(int soLuongXuat) { 
        this.soLuongXuat = soLuongXuat; 
    }
    
    public void setGiaXuat(double giaXuat) { 
        this.giaXuat = giaXuat; 
    }
    
    public void setNgayXuat(String ngayXuat) { 
        this.ngayXuat = ngayXuat; 
    }

    public void setLoaiHangHoa(String loaiHangHoa) { 
        this.loaiHangHoa = loaiHangHoa; 
    }
}
