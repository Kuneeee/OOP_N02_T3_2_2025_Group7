public class nhap
{
   public String hanghoaID;
   public String tenHangHoa;
   public int soLuongNhap;
   public double giaNhap;
   public String ngayNhap;
   nhap(){};
   
   public nhap(String hanghoaID, String tenHangHoa, int soLuongNhap, double giaNhap, String ngayNhap) {
      this.hanghoaID = hanghoaID;
      this.tenHangHoa = tenHangHoa;
      this.soLuongNhap = soLuongNhap;
      this.giaNhap = giaNhap;
      this.ngayNhap = ngayNhap;
   }
}