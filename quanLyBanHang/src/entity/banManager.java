import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class banManager {

    private List<Ban> danhSachBan = new ArrayList<>();

    public void themBan(Ban ban) {
        danhSachBan.add(ban);
    }

    public boolean xoaBan(String hangHoaID) {
        return danhSachBan.removeIf(ban -> ban.getHangHoaID().equals(hangHoaID));
    }

    public boolean suaBan(String hangHoaID) {
        for (int i = 0; i < danhSachBan.size(); i++) {
            Ban ban = danhSachBan.get(i);
            if (ban.getHangHoaID().equals(hangHoaID)) {
                Scanner sc = new Scanner(System.in);

                System.out.print("Nhập tên hàng hóa mới: ");
                String newTenHangHoa = sc.nextLine();

                System.out.print("Nhập giá bán mới: ");
                double newGiaBan = sc.nextDouble();

                System.out.print("Nhập số lượng bán mới: ");
                int newSoLuongBan = sc.nextInt();

                ban.setTenHangHoa(newTenHangHoa);
                ban.setGiaBan(newGiaBan);
                ban.setSoLuongBan(newSoLuongBan);

                sc.close();
                return true;
            }
        }
        return false;
    }

    // Hiển thị danh sách các ban
    public void hienThiDanhSach() {
        if (danhSachBan.isEmpty()) {
            System.out.println("Danh sách ban hiện tại trống!");
            return;
        }

        for (Ban ban : danhSachBan) {
            System.out.println("Mã hàng hóa: " + ban.getHangHoaID());
            System.out.println("Tên hàng hóa: " + ban.getTenHangHoa());
            System.out.println("Giá bán: " + ban.getGiaBan());
            System.out.println("Số lượng bán: " + ban.getSoLuongBan());
            System.out.println();
        }
    }

    // Thêm getter để lấy danh sách cho bên ngoài dùng
    public List<Ban> getDanhSachBan() {
        return danhSachBan;
    }
}
