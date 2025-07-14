
import java.util.Scanner;

public class app {

   public static void nhapHang(String hanghoaID, String tenHangHoa, int soLuongNhap, double giaNhap, String ngayNhap) {
      if (hanghoaID != null && !hanghoaID.isEmpty() && tenHangHoa != null && !tenHangHoa.isEmpty() && soLuongNhap > 0 && giaNhap > 0 && ngayNhap != null && !ngayNhap.isEmpty()) {
         nhap hangHoaNhap = new nhap(hanghoaID, tenHangHoa, soLuongNhap, giaNhap, ngayNhap);
         System.out.println("Thông tin hàng hóa đã được nhập thành công.");
      } else {
         System.out.println("Thông tin nhập không hợp lệ.");
      }
   }

    public static void xuatHang(String hanghoaID, String tenHangHoa, int soLuongBan, int giaBan) {
      if (hanghoaID != null && !hanghoaID.isEmpty() && tenHangHoa != null && !tenHangHoa.isEmpty() && soLuongBan > 0 && giaBan > 0) {
         ban hangHoaXuat = new ban(hanghoaID, tenHangHoa, soLuongBan, giaBan);
         System.out.println("Thông tin hàng hóa đã được xuất thành công.");
      } else {
         System.out.println("Thông tin xuất không hợp lệ.");
      }
   }

   public static void capNhatHang(String hanghoaID, String tenHangHoa, int soLuongHangHoa, String nhaSanXuat, int namSanXuat, String loaiHangHoa) {
      hangHoa hangHoa = new hangHoa(hanghoaID, tenHangHoa, soLuongHangHoa, nhaSanXuat, namSanXuat, loaiHangHoa);
      System.out.println("Thông tin hàng hóa đã được cập nhật thành công.");
   }
   
   public static void main(String[] args) 
   {
      /* test 1
      Scanner input = new Scanner(System.in);

      breakAndContinue.test();

      number n = new number();
      passObject.f(n);
      System.out.println(n.i);

      int a;
      a = randNum.RandNum();
      System.out.println("random number " + a);

      int b = input.nextInt();
      if (shortCircuit.test1(b) == true) System.out.println("be hon 10");
      else if (shortCircuit.test2(b) == true) System.out.println("be hon 20");
      else if (shortCircuit.test3(b) == true) System.out.println("be hon 30");
      input.close();

      switchCake.test();
      */
      
      /* test 2
      hangHoa myObj = new hangHoa(1, "banh mi", "long dep zai", 2025, "do an");
      System.out.println("hang hoa id: " + myObj.hang_hoa_ID);
      System.out.println("ten hang hoa: " + myObj.ten_hang_hoa);
      System.out.println("nha san xuat: " + myObj.nha_sx);
      System.out.println("loai hang hoa: " + myObj.loai_hang_hoa);
      System.out.println("nam san xuat: " + myObj.nam_sx);
      */
      nhapHang("HH001", "Sản phẩm A", 100, 50000, "2025-07-14");
      xuatHang("HH001", "Sản phẩm A", 50, 60000);
      capNhatHang("HH001", "Sản phẩm A", 50, "Nhà sản xuất A", 2025, "Loại A");
   }
}
