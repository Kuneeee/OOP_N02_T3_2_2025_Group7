public class ban {
   // Thuộc tính của hàng hóa
   public String hangHoaID;
   public String tenHangHoa;
   public int soLuongBan;
   public int giaBan;
   
   public ban() {}

   public ban(String hangHoaID, String tenHangHoa, int soLuongBan, int giaBan) {
      this.hangHoaID = hangHoaID;
      this.tenHangHoa = tenHangHoa;
      this.soLuongBan = soLuongBan;
      this.giaBan = giaBan;
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
   public int getSoLuongBan() {
      return soLuongBan;
   }
   public void setSoLuongBan(int soLuongBan) {
      this.soLuongBan = soLuongBan;
   }
   public int getGiaBan() {
      return giaBan;
   }
   public void setGiaBan(int giaBan) {
      this.giaBan = giaBan;
   }
   public int tinhTongTien() {
      return soLuongBan * giaBan;
   }
   @Override
   public String toString() {
      return "ban{" +
              "hangHoaID='" + hangHoaID + '\'' +
              ", tenHangHoa='" + tenHangHoa + '\'' +
              ", soLuongBan=" + soLuongBan +
              ", tongTien=" + tinhTongTien() +
              '}';
   }
}
