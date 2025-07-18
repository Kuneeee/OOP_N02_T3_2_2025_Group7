public class Ban {
    private String hangHoaID;   // Mã hàng hóa
    private String tenHangHoa;   // Tên hàng hóa
    private double giaBan;       // Giá bán của hàng hóa
    private int soLuongBan;      // Số lượng bán của hàng hóa

    // Constructor để khởi tạo đối tượng ban
    public Ban(String hangHoaID, String tenHangHoa, double giaBan, int soLuongBan) {
        this.hangHoaID = hangHoaID;
        this.tenHangHoa = tenHangHoa;
        this.giaBan = giaBan;
        this.soLuongBan = soLuongBan;
    }

    // Getter và Setter cho các thuộc tính

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

    // Chỉnh sửa setter để nhận giá trị kiểu double
    public void setGiaBan(double giaBan) {
        this.giaBan = giaBan;
    }

    public int getSoLuongBan() {
        return soLuongBan;
    }

    public void setSoLuongBan(int soLuongBan) {
        this.soLuongBan = soLuongBan;
    }

    // Phương thức để hiển thị thông tin của một ban
    public void hienThiThongTin() {
        System.out.println("Mã hàng hóa: " + hangHoaID);
        System.out.println("Tên hàng hóa: " + tenHangHoa);
        System.out.println("Giá bán: " + giaBan);
        System.out.println("Số lượng bán: " + soLuongBan);
        System.out.println();
    }
}
