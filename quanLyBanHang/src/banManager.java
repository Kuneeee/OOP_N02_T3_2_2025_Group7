import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class banManager {
    // Danh sách các ban
    private List<Ban> danhSachBan = new ArrayList<>();

    // Thêm một ban vào danh sách
    public void themBan(Ban ban) {
        danhSachBan.add(ban);
    }

    // Xóa ban theo mã hàng hóa
    public boolean xoaBan(String hangHoaID) {
        return danhSachBan.removeIf(ban -> ban.getHangHoaID().equals(hangHoaID));
    }

    // Sửa thông tin ban theo mã hàng hóa
    public boolean suaBan(String hangHoaID) {
        for (int i = 0; i < danhSachBan.size(); i++) {
            Ban ban = danhSachBan.get(i);
            if (ban.getHangHoaID().equals(hangHoaID)) {
                Scanner sc = new Scanner(System.in);

                // Nhập các thông tin mới từ người dùng
                System.out.print("Nhập tên hàng hóa mới: ");
                String newTenHangHoa = sc.nextLine();

                System.out.print("Nhập giá bán mới: ");
                double newGiaBan = sc.nextDouble();

                System.out.print("Nhập số lượng bán mới: ");
                int newSoLuongBan = sc.nextInt();

                // Cập nhật thông tin cho ban
                ban.setTenHangHoa(newTenHangHoa);
                ban.setGiaBan(newGiaBan);
                ban.setSoLuongBan(newSoLuongBan);

                // Đóng Scanner
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

    // Tìm kiếm ban theo tên
    public List<Ban> timBanTheoTen(String ten) {
        List<Ban> ketQua = new ArrayList<>();
        for (Ban ban : danhSachBan) {
            if (ban.getTenHangHoa().toLowerCase().contains(ten.toLowerCase())) {
                ketQua.add(ban);
            }
        }
        return ketQua;
    }
}
