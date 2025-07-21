import java.util.Scanner;
import java.util.ArrayList;

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

      // test
      ArrayList<HangHoa> danhSachHangHoa = new ArrayList<>();
      Scanner input = new Scanner(System.in);
      
      while (true) {
         System.out.println("\n=== QUẢN LÝ BÁN HÀNG ===");
         System.out.println("Nhập thông tin hàng hóa (hoặc 'exit' để thoát):");
         System.out.println("===================================");
         System.out.println("Cac lua chon:");
         System.out.println("1. Nhập hàng hóa");
         System.out.println("2. Bán hàng hóa");
         System.out.println("3. Xem danh sách hàng hóa");
         System.out.println("4. Tìm kiếm hàng hóa");
         System.out.println("5. Tính doanh thu");
         System.out.println("6. Thoát");
         System.out.print("Chọn tùy chọn (1-5): ");
         int choice = input.nextInt();
         input.nextLine();
         if (choice == 1)
         {
            System.out.print("Nhập ID hàng hóa: ");
            String hanghoaID = input.nextLine();
            System.out.print("Nhập tên hàng hóa: ");
            String tenHangHoa = input.nextLine();
            System.out.print("Nhập số lượng nhập: ");
            int soLuongNhap = input.nextInt();
            System.out.print("Nhập giá nhập: ");
            double giaNhap = input.nextDouble();
            input.nextLine(); // Clear the newline character
            System.out.print("Nhập ngày nhập (dd/MM/yyyy): ");
            String ngayNhap = input.nextLine();
            System.out.print("Nhập loại hàng hóa: ");
            String loaiHangHoa = input.nextLine();

            // Tạo đối tượng Nhap và thêm vào danh sách
            Nhap nhapHangHoa = new Nhap(hanghoaID, tenHangHoa, soLuongNhap, giaNhap, ngayNhap, loaiHangHoa);
            danhSachHangHoa.add(nhapHangHoa);
         }
         else if (choice == 2)
         {
            // Bán hàng hóa
         }
         else if (choice == 3)
         {
            // Hiển thị danh sách hàng hóa
            if (danhSachHangHoa.isEmpty()) {
               System.out.println("Danh sách hàng hóa hiện tại trống!");
            } else {
               System.out.println("Danh sách hàng hóa:");
               for (HangHoa hangHoa : danhSachHangHoa) {
                  System.out.println("ID: " + hangHoa.getHanghoaID() +
                                     ", Tên: " + hangHoa.getTenHangHoa() +
                                     ", Số lượng: " + hangHoa.getSoLuongHangHoa() +
                                     ", Ngày nhập: " + hangHoa.getNgayNhap() +
                                     ", Giá nhập: " + hangHoa.getGiaNhap() +
                                     ", Loại: " + hangHoa.getLoaiHangHoa());
               }
            }
         }
         else if (choice == 4)
         {
            // Tìm kiếm hàng hóa
         }
         else if (choice == 5)
         {
            // Tính doanh thu
         }
         else if (choice == 6)
         {
            // Thoát chương trình
            break;
         }
         else
         {
            System.out.println("Lựa chọn không hợp lệ. Vui lòng chọn lại.");
         }
      }
      System.out.println("Cảm ơn bạn đã sử dụng chương trình quản lý bán hàng!");
      System.out.println("Kết thúc chương trình.");
      input.close();
   }
}