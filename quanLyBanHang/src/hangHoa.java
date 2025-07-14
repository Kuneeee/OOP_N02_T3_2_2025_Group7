public class hangHoa {
    // Thuộc tính của hàng hóa
    public String hanghoaID;
    public String tenHangHoa;
    public int soLuongHangHoa;
    public String nhaSanXuat;
    public int namSanXuat;
    public String loaiHangHoa;

    public hangHoa(String hanghoaID, String tenHangHoa, int soLuongHangHoa, String nhaSanXuat, int namSanXuat, String loaiHangHoa) {
        // Khởi tạo các thuộc tính của hàng hóa
        this.hanghoaID = hanghoaID;
        this.tenHangHoa = tenHangHoa;
        this.soLuongHangHoa = soLuongHangHoa;
        this.nhaSanXuat = nhaSanXuat;
        this.namSanXuat = namSanXuat;
        this.loaiHangHoa = loaiHangHoa;
    }

}