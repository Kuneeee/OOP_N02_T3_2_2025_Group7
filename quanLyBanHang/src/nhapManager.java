import java.util.ArrayList;

public class nhapManager {
    private ArrayList<hangHoa> danhSachHangHoa = new ArrayList<>();

    public void addHangHoa(hangHoa hh) {
        danhSachHangHoa.add(hh);
    }

    public ArrayList<hangHoa> getAllHangHoa() {
        return danhSachHangHoa;
    }

    public hangHoa findByID(String hanghoaID) {
        for (hangHoa hh : danhSachHangHoa) {
            if (hh.hanghoaID.equals(hanghoaID)) {
                return hh;
            }
        }
        return null;
    }

    public void printAll() {
        for (hangHoa hh : danhSachHangHoa) {
            System.out.println(hh.hanghoaID + " - " + hh.tenHangHoa + " - " + hh.soLuongHangHoa + " - " +
                               hh.nhaSanXuat + " - " + hh.namSanXuat + " - " + hh.loaiHangHoa);
        }
    }
}