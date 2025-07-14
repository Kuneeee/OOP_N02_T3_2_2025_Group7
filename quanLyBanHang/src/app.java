import java.util.Scanner;

public class app {
   // Phương thức nhập thông tin hàng hóa
   public static void nhapHang(String hanghoaID, String tenHangHoa, int soLuongNhap, double giaNhap, String ngayNhap) 
   {
      // Kiểm tra thông tin nhập
      if (hanghoaID != null && !hanghoaID.isEmpty() && tenHangHoa != null && !tenHangHoa.isEmpty() && soLuongNhap > 0 && giaNhap > 0 && ngayNhap != null && !ngayNhap.isEmpty()) {
         nhap hangHoaNhap = new nhap(hanghoaID, tenHangHoa, soLuongNhap, giaNhap, ngayNhap);
         System.out.println("Thông tin hàng hóa đã được nhập thành công.");
      } else {
         System.out.println("Thông tin nhập không hợp lệ.");
      }
   }

   // Phuơng thức xuất thông tin hàng hóa
   public static void xuatHang(String hanghoaID, String tenHangHoa, int soLuongNhap, int giaNhap, String ngayNhap) 
   {
      // Kiểm tra thông tin xuất
      if (hanghoaID != null && !hanghoaID.isEmpty() && tenHangHoa != null && !tenHangHoa.isEmpty() && soLuongNhap > 0 && giaNhap > 0) {
         xuat hangHoaXuat = new xuat(hanghoaID, tenHangHoa, soLuongNhap, giaNhap, ngayNhap);
         System.out.println("Thông tin hàng hóa đã được xuất thành công.");
      } else {
         System.out.println("Thông tin xuất không hợp lệ.");
      }
   }

   // Phương thức cập nhật thông tin hàng hóa
   public static void capNhatHang(String hanghoaID, String tenHangHoa, int soLuongHangHoa, String nhaSanXuat, int namSanXuat, String loaiHangHoa) 
   {
      hangHoa hangHoa = new hangHoa(hanghoaID, tenHangHoa, soLuongHangHoa, nhaSanXuat, namSanXuat, loaiHangHoa);
      System.out.println("Thông tin hàng hóa đã được cập nhật thành công.");
   }

   // Main chạy chương trình
   public static void main(String[] args) 
   {
      /*
      //test 1
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
      
      //test 2
      hangHoa myObj = new hangHoa(1, "banh mi", "long dep zai", 2025, "do an");
      System.out.println("hang hoa id: " + myObj.hang_hoa_ID);
      System.out.println("ten hang hoa: " + myObj.ten_hang_hoa);
      System.out.println("nha san xuat: " + myObj.nha_sx);
      System.out.println("loai hang hoa: " + myObj.loai_hang_hoa);
      System.out.println("nam san xuat: " + myObj.nam_sx);
      */

      // Khai báo các biến để nhập thông tin hàng hóa
      String hanghoaID;
      String tenHangHoa;
      int soLuongNhap;
      double giaNhap;
      String ngayNhap;

      // Nhập thông tin hàng hóa
      Scanner input = new Scanner(System.in);
      System.out.print("Nhập ID hàng hóa: ");
      hanghoaID = input.nextLine();
      System.out.print("Nhập tên hàng hóa: ");
      tenHangHoa = input.nextLine();
      System.out.print("Nhập số lượng nhập: ");
      soLuongNhap = input.nextInt();
      System.out.print("Nhập giá nhập: ");
      giaNhap = input.nextDouble();
      input.nextLine(); // Đọc dòng mới
      System.out.print("Nhập ngày nhập (dd-MM-yyyy): ");
      ngayNhap = input.nextLine();
      input.close();

      // Gọi các phương thức để nhập, cập nhật và xuất hàng hóa
      nhapHang(hanghoaID, tenHangHoa, soLuongNhap, giaNhap, ngayNhap);
      capNhatHang(hanghoaID, tenHangHoa, soLuongNhap, ngayNhap, soLuongNhap, tenHangHoa);
      xuatHang(hanghoaID, tenHangHoa, soLuongNhap, soLuongNhap, ngayNhap);
   }
}