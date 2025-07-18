public class Ban {
    private String hangHoaID;
    private String tenHangHoa;
    private double giaBan;
    private int soLuongBan;

    // Constructor
    public Ban(String hangHoaID, String tenHangHoa, double giaBan, int soLuongBan) {
        this.hangHoaID = hangHoaID;
        this.tenHangHoa = tenHangHoa;
        this.giaBan = giaBan;
        this.soLuongBan = soLuongBan;
    }

    // Getter và Setter cho hangHoaID
    public String getHangHoaID() {
        return hangHoaID;
    }

    public void setHangHoaID(String hangHoaID) {
        this.hangHoaID = hangHoaID;
    }

    // Getter và Setter cho tenHangHoa
    public String getTenHangHoa() {
        return tenHangHoa;
    }

    public void setTenHangHoa(String tenHangHoa) {
        this.tenHangHoa = tenHangHoa;
    }

    // Getter và Setter cho giaBan
    public double getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(double giaBan) {
        this.giaBan = giaBan;
    }

    // Getter và Setter cho soLuongBan
    public int getSoLuongBan() {
        return soLuongBan;
    }

    public void setSoLuongBan(int soLuongBan) {
        this.soLuongBan = soLuongBan;
    }
}
