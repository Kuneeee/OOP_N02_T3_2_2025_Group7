import javax.persistence.*;

@Entity
@Table(name = "hang_hoa")
@Inheritance(strategy = InheritanceType.JOINED)
public class HangHoa {
    // Thuộc tính của hàng hóa
    @Id
    private String hanghoaID;
    
    @Column(name = "ten_hang_hoa")
    private String tenHangHoa;
    
    @Column(name = "so_luong_hang_hoa")
    private int soLuongHangHoa;
    
    @Column(name = "ngay_nhap")
    private String ngayNhap;
    
    @Column(name = "gia_nhap")
    private Double giaNhap;
    
    @Column(name = "loai_hang_hoa")
    private String loaiHangHoa;

    // Default constructor
    public HangHoa() {}

    // Parameterized constructor
    public HangHoa(String hanghoaID, String tenHangHoa, int soLuongHangHoa, String ngayNhap, Double giaNhap, String loaiHangHoa) {
        // Khởi tạo các thuộc tính của hàng hóa
        this.hanghoaID = hanghoaID;
        this.tenHangHoa = tenHangHoa;
        this.soLuongHangHoa = soLuongHangHoa;
        this.ngayNhap = ngayNhap;
        this.giaNhap = giaNhap;
        this.loaiHangHoa = loaiHangHoa;
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

    public String getNgayNhap() { 
        return ngayNhap; 
    }

    public Double getGiaNhap() { 
        return giaNhap; 
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

    public void setNgayNhap(String ngayNhap) { 
        this.ngayNhap = ngayNhap; 
    }

    public void setGiaNhap(Double giaNhap) { 
        this.giaNhap = giaNhap; 
    }
    
    public void setLoaiHangHoa(String loaiHangHoa) { 
        this.loaiHangHoa = loaiHangHoa; 
    }
}