import jakarta.persistence.*;

@Entity
@Table(name = "ban")
public class Ban extends HangHoa {
    @Id
    private String hangHoaID;   
    
    @Column(name = "ten_hang_hoa")
    private String tenHangHoa;   
    
    @Column(name = "gia_ban")
    private double giaBan;       
    
    @Column(name = "so_luong_ban")
    private int soLuongBan;    
    // Default constructor for JPA
    public Ban() {
        super();
    }
    
    public Ban(String hangHoaID, String tenHangHoa, double giaBan, int soLuongBan) {
        this.hangHoaID = hangHoaID;
        this.tenHangHoa = tenHangHoa;
        this.giaBan = giaBan;
        this.soLuongBan = soLuongBan;
    }
    public String getHangHoaID() {
        return hangHoaID;
    }
    public void setHangHoaID(String hangHoaID) {
        this.hangHoaID = hangHoaID;
    }
    public String getTenHangHoa() {
        return tenHangHoa;
    }
    public void setTenHangHoa(String tenHangHoa) {
        this.tenHangHoa = tenHangHoa;
    }
    public double getGiaBan() {
        return giaBan;
    }
    public void setGiaBan(double giaBan) {
        this.giaBan = giaBan;
    }
    public int getSoLuongBan() {
        return soLuongBan;
    }
    public void setSoLuongBan(int soLuongBan) {
        this.soLuongBan = soLuongBan;
    }
    public void hienThiThongTin() {
        System.out.println("Mã hàng hóa: " + hangHoaID);
        System.out.println("Tên hàng hóa: " + tenHangHoa);
        System.out.println("Giá bán: " + giaBan);
        System.out.println("Số lượng bán: " + soLuongBan);
        System.out.println();
    }
}
