public class xuat 
{
    // Thuộc tính của hàng hóa xuất
    public String hanghoaID;
    public String tenHangHoa;
    public int soLuongNhap;
    public double giaNhap;
    public String ngayNhap;
    xuat(){};

    public xuat(String hanghoaID, String tenHangHoa, int soLuongNhap, double giaNhap, String ngayNhap) 
    {
        // Khởi tạo các thuộc tính của hàng hóa xuất
        this.hanghoaID = hanghoaID;
        this.tenHangHoa = tenHangHoa;
        this.soLuongNhap = soLuongNhap;
        this.giaNhap = giaNhap;
        this.ngayNhap = ngayNhap;

        // In thông tin hàng hóa xuất
        System.out.println("Thông tin xuất hàng:");
        System.out.println("ID hàng hóa: " + hanghoaID);
        System.out.println("Tên hàng hóa: " + tenHangHoa);
        System.out.println("Số lượng xuất: " + soLuongNhap);
        System.out.println("Giá xuất: " + giaNhap);
        System.out.println("Ngày xuất: " + ngayNhap);
   } 
}
