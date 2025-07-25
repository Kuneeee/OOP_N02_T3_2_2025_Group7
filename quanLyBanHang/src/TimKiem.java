import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TimKiem {

    private List<Ban> danhSachBan;

    // Constructor nhận danh sách để tìm kiếm
    public TimKiem(List<Ban> danhSachBan) {
        this.danhSachBan = danhSachBan;
    }

    // Tìm các Ban theo tên hàng hóa (không phân biệt hoa thường)
    public List<Ban> timBanTheoTen(String ten) {
        List<Ban> ketQua = new ArrayList<>();
        for (Ban ban : danhSachBan) {
            if (ban.getTenHangHoa().toLowerCase().contains(ten.toLowerCase())) {
                ketQua.add(ban);
            }
        }
        return ketQua;
    }

    // Tương tác với người dùng để tìm kiếm và hiển thị kết quả
    public void timKiemBan(Scanner sc) {
        System.out.print("Nhập tên hàng hóa cần tìm: ");
        String tuKhoa = sc.nextLine();

        List<Ban> ketQua = timBanTheoTen(tuKhoa);

        if (ketQua.isEmpty()) {
            System.out.println("Không tìm thấy hàng hóa nào phù hợp với từ khóa: " + tuKhoa);
        } else {
            System.out.println("Kết quả tìm kiếm:");
            for (Ban ban : ketQua) {
                ban.hienThiThongTin();
            }
        }
    }
}
