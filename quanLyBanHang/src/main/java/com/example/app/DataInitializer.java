package com.example.app;

import entity.HangHoa;
import entity.KhachHang;
import repository.HangHoaRepository;
import repository.KhachHangRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private HangHoaRepository hangHoaRepository;

    @Autowired
    private KhachHangRepository khachHangRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // Force clear and re-initialize data for testing
        System.out.println("=== FORCE CLEARING AND REINITIALIZING DATA ===");
        
        // Clear existing data
        khachHangRepository.deleteAll();
        hangHoaRepository.deleteAll();
        
        System.out.println("Initializing sample data...");
            
            // Initialize HangHoa data
            HangHoa hh1 = new HangHoa();
            hh1.setHanghoaID("HH001");
            hh1.setTenHangHoa("Bánh quy Oreo");
            hh1.setSoLuongHangHoa(100);
            hh1.setLoaiHangHoa("Bánh kẹo");
            hh1.setGiaNhap(new BigDecimal("15000"));
            hh1.setNhaSanXuat("Mondelez");
            hh1.setNamSanXuat(2024);
            hh1.setNgayNhap(LocalDateTime.now());
            hangHoaRepository.save(hh1);

            HangHoa hh2 = new HangHoa();
            hh2.setHanghoaID("HH002");
            hh2.setTenHangHoa("Kẹo dẻo Haribo");
            hh2.setSoLuongHangHoa(80);
            hh2.setLoaiHangHoa("Bánh kẹo");
            hh2.setGiaNhap(new BigDecimal("25000"));
            hh2.setNhaSanXuat("Haribo");
            hh2.setNamSanXuat(2024);
            hh2.setNgayNhap(LocalDateTime.now());
            hangHoaRepository.save(hh2);

            HangHoa hh3 = new HangHoa();
            hh3.setHanghoaID("HH003");
            hh3.setTenHangHoa("Snack khoai tây Lay's");
            hh3.setSoLuongHangHoa(150);
            hh3.setLoaiHangHoa("Đồ ăn vặt");
            hh3.setGiaNhap(new BigDecimal("12000"));
            hh3.setNhaSanXuat("PepsiCo");
            hh3.setNamSanXuat(2024);
            hh3.setNgayNhap(LocalDateTime.now());
            hangHoaRepository.save(hh3);

            HangHoa hh4 = new HangHoa();
            hh4.setHanghoaID("HH004");
            hh4.setTenHangHoa("Nước ngọt Coca Cola");
            hh4.setSoLuongHangHoa(200);
            hh4.setLoaiHangHoa("Đồ uống");
            hh4.setGiaNhap(new BigDecimal("10000"));
            hh4.setNhaSanXuat("Coca Cola");
            hh4.setNamSanXuat(2024);
            hh4.setNgayNhap(LocalDateTime.now());
            hangHoaRepository.save(hh4);

            HangHoa hh5 = new HangHoa();
            hh5.setHanghoaID("HH005");
            hh5.setTenHangHoa("Mì tôm Hảo Hảo");
            hh5.setSoLuongHangHoa(300);
            hh5.setLoaiHangHoa("Thực phẩm");
            hh5.setGiaNhap(new BigDecimal("4000"));
            hh5.setNhaSanXuat("Acecook Việt Nam");
            hh5.setNamSanXuat(2024);
            hh5.setNgayNhap(LocalDateTime.now());
            hangHoaRepository.save(hh5);

            System.out.println("Sample HangHoa data initialized successfully!");

        // Initialize KhachHang data
            KhachHang kh1 = new KhachHang();
            kh1.setKhachHangId("KH001");
            kh1.setTenKhachHang("Nguyễn Văn Nam");
            kh1.setSoDienThoai("0901234567");
            kh1.setEmail("nam.nguyen@email.com");
            kh1.setDiaChi("123 Đường ABC, Quận 1, TP.HCM");
            kh1.setLoaiKhachHang("VIP");
            kh1.setDiemThuong(500);
            kh1.setTongChiTieu(new BigDecimal("2000000"));
            kh1.setTongDaMua(new BigDecimal("50"));
            kh1.setSoDonHang(12);
            kh1.setNgayThamGia(LocalDate.now().minusMonths(6));
            kh1.setNgayTao(LocalDateTime.now().minusMonths(6));
            khachHangRepository.save(kh1);

            KhachHang kh2 = new KhachHang();
            kh2.setKhachHangId("KH002");
            kh2.setTenKhachHang("Trần Thị Lan");
            kh2.setSoDienThoai("0907654321");
            kh2.setEmail("lan.tran@email.com");
            kh2.setDiaChi("456 Đường XYZ, Quận 3, TP.HCM");
            kh2.setLoaiKhachHang("Thường");
            kh2.setDiemThuong(200);
            kh2.setTongChiTieu(new BigDecimal("800000"));
            kh2.setTongDaMua(new BigDecimal("25"));
            kh2.setSoDonHang(8);
            kh2.setNgayThamGia(LocalDate.now().minusMonths(3));
            kh2.setNgayTao(LocalDateTime.now().minusMonths(3));
            khachHangRepository.save(kh2);

            KhachHang kh3 = new KhachHang();
            kh3.setKhachHangId("KH003");
            kh3.setTenKhachHang("Lê Minh Đức");
            kh3.setSoDienThoai("0912345678");
            kh3.setEmail("duc.le@email.com");
            kh3.setDiaChi("789 Đường DEF, Quận 7, TP.HCM");
            kh3.setLoaiKhachHang("VIP");
            kh3.setDiemThuong(750);
            kh3.setTongChiTieu(new BigDecimal("3500000"));
            kh3.setTongDaMua(new BigDecimal("80"));
            kh3.setSoDonHang(20);
            kh3.setNgayThamGia(LocalDate.now().minusMonths(12));
            kh3.setNgayTao(LocalDateTime.now().minusMonths(12));
            khachHangRepository.save(kh3);

            System.out.println("Sample KhachHang data initialized successfully!");
    }
}
