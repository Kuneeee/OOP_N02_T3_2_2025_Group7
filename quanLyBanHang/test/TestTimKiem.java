import java.util.Scanner;

public class TestTimKiem {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        banManager manager = new banManager();

        // Thêm một số dữ liệu giả vào danh sách
        manager.themBan(new Ban("001", "Bánh mì", 15000, 20));
        manager.themBan(new Ban("002", "Sữa tươi", 22000, 10));
        manager.themBan(new Ban("003", "Cà phê", 30000, 15));

        System.out.println("=== TEST CHỨC NĂNG TÌM KIẾM ===");
        System.out.print("Nhập tên hàng hóa cần tìm: ");
        manager.timKiemBan(input);

        input.close();
    }
}
