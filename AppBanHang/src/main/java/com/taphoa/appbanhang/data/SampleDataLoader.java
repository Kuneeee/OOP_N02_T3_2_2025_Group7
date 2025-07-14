package com.taphoa.appbanhang.data;

import com.taphoa.appbanhang.entity.Hanghoa;
import com.taphoa.appbanhang.entity.Nhap;
import com.taphoa.appbanhang.entity.XuatBan;
import com.taphoa.appbanhang.service.HanghoaService;
import com.taphoa.appbanhang.service.NhapService;
import com.taphoa.appbanhang.service.XuatBanService;
import com.taphoa.appbanhang.service.DoanhThuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.time.LocalDate;

@Component
public class SampleDataLoader {

    @Autowired
    private HanghoaService hanghoaService;

    @Autowired
    private NhapService nhapService;

    @Autowired
    private XuatBanService xuatBanService;

    @Autowired
    private DoanhThuService doanhThuService;

    @PostConstruct
    public void init() {
        // Chỉ load dữ liệu mẫu nếu database trống
        if (hanghoaService.getAllHanghoa().isEmpty()) {
            loadSampleData();
        }
    }

    private void loadSampleData() {
        try {
            // Tạo một số hàng hóa mẫu
            Hanghoa hanghoa1 = new Hanghoa("Nước Coca Cola", 50, "Coca Cola Company", 2024, "Đồ uống");
            Hanghoa hanghoa2 = new Hanghoa("Bánh Oreo", 30, "Mondelez International", 2024, "Bánh kẹo");
            Hanghoa hanghoa3 = new Hanghoa("Gạo ST25", 100, "Nông sản Việt", 2024, "Thực phẩm");
            Hanghoa hanghoa4 = new Hanghoa("Dầu ăn Simply", 25, "Wilmar International", 2024, "Gia vị");
            Hanghoa hanghoa5 = new Hanghoa("Mì tôm Hảo Hảo", 200, "Acecook Việt Nam", 2024, "Thực phẩm");

            // Lưu hàng hóa
            hanghoa1 = hanghoaService.saveHanghoa(hanghoa1);
            hanghoa2 = hanghoaService.saveHanghoa(hanghoa2);
            hanghoa3 = hanghoaService.saveHanghoa(hanghoa3);
            hanghoa4 = hanghoaService.saveHanghoa(hanghoa4);
            hanghoa5 = hanghoaService.saveHanghoa(hanghoa5);

            // Tạo một số phiếu nhập
            Nhap nhap1 = new Nhap(hanghoa1, hanghoa1.getTenHangHoa(), 50, 15000.0, LocalDate.now().minusDays(5));
            Nhap nhap2 = new Nhap(hanghoa2, hanghoa2.getTenHangHoa(), 30, 25000.0, LocalDate.now().minusDays(4));
            Nhap nhap3 = new Nhap(hanghoa3, hanghoa3.getTenHangHoa(), 100, 50000.0, LocalDate.now().minusDays(3));

            nhapService.createNhap(nhap1);
            nhapService.createNhap(nhap2);
            nhapService.createNhap(nhap3);

            // Tạo một số phiếu xuất bán
            XuatBan xuatBan1 = new XuatBan(hanghoa1, hanghoa1.getTenHangHoa(), 10, 18000.0, LocalDate.now().minusDays(2));
            XuatBan xuatBan2 = new XuatBan(hanghoa2, hanghoa2.getTenHangHoa(), 5, 30000.0, LocalDate.now().minusDays(1));
            XuatBan xuatBan3 = new XuatBan(hanghoa3, hanghoa3.getTenHangHoa(), 20, 55000.0, LocalDate.now());

            xuatBanService.createXuatBan(xuatBan1);
            xuatBanService.createXuatBan(xuatBan2);
            xuatBanService.createXuatBan(xuatBan3);

            // Tạo báo cáo doanh thu mẫu bằng createDailyReport
            try {
                doanhThuService.createDailyReport(LocalDate.now());
            } catch (RuntimeException e) {
                // Báo cáo đã tồn tại cho ngày hôm nay
                System.out.println("Báo cáo doanh thu cho ngày hôm nay đã tồn tại.");
            }

            System.out.println("✅ Đã tải dữ liệu mẫu thành công!");
        } catch (Exception e) {
            System.err.println("❌ Lỗi khi tải dữ liệu mẫu: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
