public class testBan {

    public static void main(String[] args) {
        BanManager manager = new BanManager();

        Ban ban1 = new Ban("H001", "Bàn gỗ", 500000, 10);
        Ban ban2 = new Ban("H002", "Bàn kính", 300000, 5);
        Ban ban3 = new Ban("H003", "Bàn nhựa", 150000, 20);

        manager.themBan(ban1);
        manager.themBan(ban2);
        manager.themBan(ban3);

        System.out.println("===== Danh sách sau khi thêm các Ban: =====");
        manager.hienThiDanhSach();

        manager.xoaBan("H002");
        System.out.println("===== Danh sách sau khi xóa Bàn có Mã H002: =====");
        manager.hienThiDanhSach();

        manager.suaBan("H001");
        System.out.println("===== Danh sách sau khi sửa Bàn có Mã H001: =====");
        manager.hienThiDanhSach();
    }
}
