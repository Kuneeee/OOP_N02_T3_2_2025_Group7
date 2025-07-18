import java.util.List;

public class testBan {

    public static void main(String[] args) {
        // Tạo đối tượng quản lý ban
        banManager manager = new banManager();

        // Thêm các ban vào danh sách
        Ban ban1 = new Ban("HH001", "Bàn làm việc", 500.0, 10);
        Ban ban2 = new Ban("HH002", "Ghế văn phòng", 200.0, 20);
        manager.themBan(ban1);
        manager.themBan(ban2);

        // Hiển thị danh sách ban
        System.out.println("Danh sách các ban sau khi thêm:");
        manager.hienThiDanhSach();

        // Sửa thông tin của ban1
        System.out.println("Sửa thông tin của ban HH001:");
        manager.suaBan("HH001");

        // Hiển thị danh sách ban sau khi sửa
        System.out.println("Danh sách các ban sau khi sửa:");
        manager.hienThiDanhSach();

        // Xóa ban2
        System.out.println("Xóa ban HH002:");
        manager.xoaBan("HH002");

        // Hiển thị danh sách ban sau khi xóa
        System.out.println("Danh sách các ban sau khi xóa:");
        manager.hienThiDanhSach();

        // Tìm kiếm ban theo tên
        System.out.println("Tìm kiếm ban theo tên 'bàn':");
        List<Ban> ketQuaTimKiem = manager.timBanTheoTen("bàn");

        // Hiển thị kết quả tìm kiếm
        if (ketQuaTimKiem.isEmpty()) {
            System.out.println("Không tìm thấy ban nào.");
        } else {
            for (Ban ban : ketQuaTimKiem) {
                System.out.println("Mã hàng hóa: " + ban.getHangHoaID());
                System.out.println("Tên hàng hóa: " + ban.getTenHangHoa());
                System.out.println("Giá bán: " + ban.getGiaBan());
                System.out.println("Số lượng bán: " + ban.getSoLuongBan());
                System.out.println();
            }
        }
    }
}
