package entity;

public class Ban {
    private String banID;
    private String hangHoaID;
    private String tenHangHoa;
    private String tenKhachHang;
    private String ngayBan;
    private double giaBan;
    private int soLuongBan;
    private double tongTien;

    public Ban() {}

    public Ban(String banID, String hangHoaID, String tenHangHoa, String tenKhachHang, String ngayBan, double giaBan, int soLuongBan) {
        this.banID = banID;
        this.hangHoaID = hangHoaID;
        this.tenHangHoa = tenHangHoa;
        this.tenKhachHang = tenKhachHang;
        this.ngayBan = ngayBan;
        this.giaBan = giaBan;
        this.soLuongBan = soLuongBan;
        this.tongTien = giaBan * soLuongBan;
    }

    public String getBanID() { return banID; }
    public void setBanID(String banID) { this.banID = banID; }
    public String getHangHoaID() { return hangHoaID; }
    public void setHangHoaID(String hangHoaID) { this.hangHoaID = hangHoaID; }
    public String getTenHangHoa() { return tenHangHoa; }
    public void setTenHangHoa(String tenHangHoa) { this.tenHangHoa = tenHangHoa; }
    public String getTenKhachHang() { return tenKhachHang; }
    public void setTenKhachHang(String tenKhachHang) { this.tenKhachHang = tenKhachHang; }
    public String getNgayBan() { return ngayBan; }
    public void setNgayBan(String ngayBan) { this.ngayBan = ngayBan; }
    public double getGiaBan() { return giaBan; }
    public void setGiaBan(double giaBan) { this.giaBan = giaBan; }
    public int getSoLuongBan() { return soLuongBan; }
    public void setSoLuongBan(int soLuongBan) { this.soLuongBan = soLuongBan; }
    public double getTongTien() { return tongTien; }
    public void setTongTien(double tongTien) { this.tongTien = tongTien; }
}
