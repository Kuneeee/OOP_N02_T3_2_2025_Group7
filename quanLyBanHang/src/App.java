import java.util.Scanner;

public class App {
   // Main chạy chương trình
   public static void main(String[] args) 
   {
      // Test code
      /*
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
      
      hangHoa myObj = new hangHoa(1, "banh mi", "long dep zai", 2025, "do an");
      System.out.println("hang hoa id: " + myObj.hang_hoa_ID);
      System.out.println("ten hang hoa: " + myObj.ten_hang_hoa);
      System.out.println("nha san xuat: " + myObj.nha_sx);
      System.out.println("loai hang hoa: " + myObj.loai_hang_hoa);
      System.out.println("nam san xuat: " + myObj.nam_sx);
      */
      // Test code

      Scanner input = new Scanner(System.in);
      System.out.println("=== CHÀO MỪNG ĐẾN HỆ THỐNG QUẢN LÝ BÁN HÀNG ===");
      
      // Nhập thông tin hàng hóa
      System.out.println("\n--- NHẬP THÔNG TIN HÀNG HÓA ---");
      System.out.print("Nhập ID hàng hóa: ");
      String hanghoaID = input.nextLine();
      
      System.out.print("Nhập tên hàng hóa: ");
      String tenHangHoa = input.nextLine();
      
      System.out.print("Nhập số lượng: ");
      int soLuong = input.nextInt();
      
      System.out.print("Nhập giá: ");
      double gia = input.nextDouble();
      input.nextLine(); // Đọc dòng mới
      
      System.out.print("Nhập ngày (dd-MM-yyyy): ");
      String ngay = input.nextLine();
      
      // Tạo đối tượng nhập hàng
      System.out.println("\n=== NHẬP HÀNG HÓA ===");
      Nhap hangNhap = new Nhap(hanghoaID, tenHangHoa, soLuong, gia, ngay);
      System.out.println("✓ Đã nhập hàng thành công!");
      
      // Tạo đối tượng xuất hàng (với cùng thông tin)
      System.out.println("\n=== XUẤT HÀNG HÓA ===");
      Xuat hangXuat = new Xuat(hanghoaID, tenHangHoa, soLuong, gia, ngay);
      System.out.println("✓ Đã xuất hàng thành công!");
      
      System.out.println("\n=== HOÀN THÀNH QUY TRÌNH ===");
      System.out.println("Cảm ơn bạn đã sử dụng hệ thống quản lý bán hàng!");
      
      input.close(); 
   }
}