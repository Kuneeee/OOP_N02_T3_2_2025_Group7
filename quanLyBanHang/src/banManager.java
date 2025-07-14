import java.util.ArrayList;
import java.util.List;

public class banManager {
    private List<ban> danhSachBan = new ArrayList<>();
    public void them(ban ban){
        danhSachBan.add(ban);
    }
    public boolean sua(String hangHoaID, ban banMoi)     {
        for (int i = 0; i < danhSachBan.size(); i++) {
            if (danhSachBan.get(i).getHangHoaID().equals(hangHoaID)) {
                danhSachBan.set(i, banMoi);
                return true;
            }
        }
        return false;
    }
    public boolean xoa(String hangHoaID) {
        return danhSachBan.removeIf(ban -> ban.getHangHoaID().equals(hangHoaID));
    }
    public List<ban> layDanhSach() {
        return danhSachBan;
    }
    public List<ban> timTheoTen(String ten) {
    List<ban> ketQua = new ArrayList<>();
    for (ban b : danhSachBan) {
        if (b.getTenHangHoa().toLowerCase().contains(ten.toLowerCase())) {
            ketQua.add(b);
        }
    }
    return ketQua; 
    }
}

