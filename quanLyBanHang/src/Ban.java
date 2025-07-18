public class Ban {
    private String hangHoaID;   
    private String tenHangHoa;   
    private double giaBan;       
    private int soLuongBan;      

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
