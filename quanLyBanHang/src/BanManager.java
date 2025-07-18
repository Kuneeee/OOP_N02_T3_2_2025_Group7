import java.util.ArrayList;
import java.util.Scanner;

public class BanManager {

    ArrayList<Ban> danhSachBan = new ArrayList<>();

    public ArrayList<Ban> themBan(Ban ban) {
        danhSachBan.add(ban);
        return danhSachBan;
    }

    public ArrayList<Ban> xoaBan(String hangHoaID) {
        for (int i = 0; i < danhSachBan.size(); i++) {
            if (danhSachBan.get(i).getHangHoaID().equals(hangHoaID)) {
                danhSachBan.remove(i);
                break;
            }
        }
        return danhSachBan;
    }

    public ArrayList<Ban> suaBan(String hangHoaID) {
        for (int i = 0; i < danhSachBan.size(); i++) {
            if (danhSachBan.get(i).getHangHoaID().equals(hangHoaID)) {
                Scanner sc = new Scanner(System.in);
                System.out.print("Nhập tên hàng hóa mới: ");
                String newTenHangHoa = sc.nextLine();
                danhSachBan.get(i).setTenHangHoa(newTenHangHoa);  

    
                System.out.print("Nhập giá bán mới: ");
                double newGiaBan = sc.nextDouble();
                danhSachBan.get(i).setGiaBan(newGiaBan);  

                System.out.print("Nhập số lượng bán mới: ");
                int newSoLuongBan = sc.nextInt();
                danhSachBan.get(i).setSoLuongBan(newSoLuongBan); 
                sc.close(); 

                break;
            }
        }
        return danhSachBan;
    }

    public void hienThiDanhSach() {
        for (Ban ban : danhSachBan) {
            System.out.println("Mã hàng hóa: " + ban.getHangHoaID());
            System.out.println("Tên hàng hóa: " + ban.getTenHangHoa());
            System.out.println("Giá bán: " + ban.getGiaBan());
            System.out.println("Số lượng bán: " + ban.getSoLuongBan());
            System.out.println();
        }
    }
}
