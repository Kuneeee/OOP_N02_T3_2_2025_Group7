public class HangHoa {
    // Thuộc tính của hàng hóa
    private String hanghoaID;
    private String tenHangHoa;
    private int soLuongHangHoa;
    private String nhaSanXuat;
    private int namSanXuat;
    private String loaiHangHoa;

    // Default constructor
    public HangHoa() {}

    // Parameterized constructor
    public HangHoa(String hanghoaID, String tenHangHoa, int soLuongHangHoa, String nhaSanXuat, int namSanXuat, String loaiHangHoa) {
        // Khởi tạo các thuộc tính của hàng hóa
        this.hanghoaID = hanghoaID;
        this.tenHangHoa = tenHangHoa;
        this.soLuongHangHoa = soLuongHangHoa;
        this.nhaSanXuat = nhaSanXuat;
        this.namSanXuat = namSanXuat;
        this.loaiHangHoa = loaiHangHoa;

        // In thông tin hàng hóa
        System.out.println("Thông tin hàng hóa:");
        System.out.println("ID hàng hóa: " + hanghoaID);
        System.out.println("Tên hàng hóa: " + tenHangHoa);
        System.out.println("Số lượng hàng hóa: " + soLuongHangHoa);
        System.out.println("Nhà sản xuất: " + nhaSanXuat);
        System.out.println("Năm sản xuất: " + namSanXuat);
        System.out.println("Loại hàng hóa: " + loaiHangHoa);
    }

    // Getter methods
    public String getHanghoaID() { 
        return hanghoaID; 
    }
    
    public String getTenHangHoa() { 
        return tenHangHoa; 
    }
    
    public int getSoLuongHangHoa() { 
        return soLuongHangHoa; 
    }
    
    public String getNhaSanXuat() { 
        return nhaSanXuat; 
    }
    
    public int getNamSanXuat() { 
        return namSanXuat; 
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
    
    public void setSoLuongHangHoa(int soLuongHangHoa) { 
        this.soLuongHangHoa = soLuongHangHoa; 
    }
    
    public void setNhaSanXuat(String nhaSanXuat) { 
        this.nhaSanXuat = nhaSanXuat; 
    }
    
    public void setNamSanXuat(int namSanXuat) { 
        this.namSanXuat = namSanXuat; 
    }
    
    public void setLoaiHangHoa(String loaiHangHoa) { 
        this.loaiHangHoa = loaiHangHoa; 
    }
}