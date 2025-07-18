public class Nhap {
    // Thuộc tính của hàng hóa nhập
    private String hanghoaID;
    private String tenHangHoa;
    private int soLuongNhap;
    private double giaNhap;
    private String ngayNhap;
   
    // Default constructor
    public Nhap() {}
   
    // Parameterized constructor
    public Nhap(String hanghoaID, String tenHangHoa, int soLuongNhap, double giaNhap, String ngayNhap) {
        // Khởi tạo các thuộc tính của hàng hóa nhập
        this.hanghoaID = hanghoaID;
        this.tenHangHoa = tenHangHoa;
        this.soLuongNhap = soLuongNhap;
        this.giaNhap = giaNhap;
        this.ngayNhap = ngayNhap;

        // In thông tin hàng hóa nhập
        System.out.println("Thông tin nhập hàng:");
        System.out.println("ID hàng hóa: " + hanghoaID);
        System.out.println("Tên hàng hóa: " + tenHangHoa);
        System.out.println("Số lượng nhập: " + soLuongNhap);
        System.out.println("Giá nhập: " + giaNhap);
        System.out.println("Ngày nhập: " + ngayNhap);
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
    
    public double getGiaNhap() { 
        return giaNhap; 
    }
    
    public String getNgayNhap() { 
        return ngayNhap; 
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
}