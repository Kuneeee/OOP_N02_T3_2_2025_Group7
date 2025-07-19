import java.util.Scanner;

public class TestNhapManager {
    public static void main(String[] args) {
        nhapManager manager = new nhapManager();
        Scanner input = new Scanner(System.in);
        System.out.print("Nhập ID hàng hóa: ");
        String id = input.nextLine();
        System.out.print("Nhập tên hàng hóa: ");
        String ten = input.nextLine();
        System.out.print("Nhập số lượng: ");
        int soLuong = input.nextInt();
        input.nextLine(); // bỏ dòng thừa
        System.out.print("Nhập nhà sản xuất: ");
        String nsx = input.nextLine();
        System.out.print("Nhập năm sản xuất: ");
        int nam = input.nextInt();
        input.nextLine();
        System.out.print("Nhập loại hàng hóa: ");
        String loai = input.nextLine();
        HangHoa hh = new HangHoa(id, ten, soLuong, nsx, nam, loai);
        manager.addHangHoa(hh);
        System.out.println("Danh sách hàng hóa:");
        input.close();
        manager.printAll();
    }
}