public class nhap
{
   // Thuộc tính của hàng hóa nhập
   public String hanghoaID;
   public String tenHangHoa;
   public int soLuongNhap;
   public double giaNhap;
   public String ngayNhap;
   nhap(){};
   
   public nhap(String hanghoaID, String tenHangHoa, int soLuongNhap, double giaNhap, String ngayNhap) {
      // Khởi tạo các thuộc tính của hàng hóa nhập
      this.hanghoaID = hanghoaID;
      this.tenHangHoa = tenHangHoa;
      this.soLuongNhap = soLuongNhap;
      this.giaNhap = giaNhap;
      this.ngayNhap = ngayNhap;
   }
}