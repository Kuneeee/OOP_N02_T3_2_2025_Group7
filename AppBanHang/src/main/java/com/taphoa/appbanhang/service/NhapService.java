package com.taphoa.appbanhang.service;

import com.taphoa.appbanhang.entity.Nhap;
import com.taphoa.appbanhang.entity.Hanghoa;
import com.taphoa.appbanhang.repository.NhapRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class NhapService {

    @Autowired
    private NhapRepository nhapRepository;

    @Autowired
    private HanghoaService hanghoaService;

    // Lấy tất cả phiếu nhập
    public List<Nhap> findAll() {
        return nhapRepository.findAll();
    }
    
    public List<Nhap> getAllNhap() {
        return findAll();
    }

    // Lấy phiếu nhập theo ID
    public Optional<Nhap> findById(Long id) {
        return nhapRepository.findById(id);
    }
    
    public Optional<Nhap> getNhapById(Long id) {
        return findById(id);
    }

    // Tạo phiếu nhập mới
    public Nhap createNhap(Nhap nhap) {
        // Kiểm tra hàng hóa tồn tại
        if (nhap.getHanghoa() == null) {
            throw new RuntimeException("Hàng hóa không được để trống");
        }

        // Cập nhật tên hàng hóa từ entity
        nhap.setTenHangHoa(nhap.getHanghoa().getTenHangHoa());
        
        // Lưu phiếu nhập
        Nhap savedNhap = nhapRepository.save(nhap);
        
        // Cập nhật số lượng hàng hóa trong kho
        hanghoaService.updateQuantity(nhap.getHanghoa().getHanghoaID(), nhap.getSoLuongNhap());
        
        return savedNhap;
    }

    // Cập nhật phiếu nhập
    public Nhap updateNhap(Long id, Nhap nhapDetails) {
        Optional<Nhap> optionalNhap = nhapRepository.findById(id);
        if (optionalNhap.isPresent()) {
            Nhap nhap = optionalNhap.get();
            
            // Hoàn tác số lượng cũ
            hanghoaService.updateQuantity(nhap.getHanghoa().getHanghoaID(), -nhap.getSoLuongNhap());
            
            // Cập nhật thông tin mới
            nhap.setHanghoa(nhapDetails.getHanghoa());
            nhap.setTenHangHoa(nhapDetails.getHanghoa().getTenHangHoa());
            nhap.setSoLuongNhap(nhapDetails.getSoLuongNhap());
            nhap.setGiaNhap(nhapDetails.getGiaNhap());
            nhap.setNgayNhap(nhapDetails.getNgayNhap());
            
            // Lưu và cập nhật số lượng mới
            Nhap savedNhap = nhapRepository.save(nhap);
            hanghoaService.updateQuantity(nhap.getHanghoa().getHanghoaID(), nhap.getSoLuongNhap());
            
            return savedNhap;
        }
        throw new RuntimeException("Không tìm thấy phiếu nhập với ID: " + id);
    }

    // Xóa phiếu nhập
    public void deleteNhap(Long id) {
        Optional<Nhap> optionalNhap = nhapRepository.findById(id);
        if (optionalNhap.isPresent()) {
            Nhap nhap = optionalNhap.get();
            // Hoàn tác số lượng đã nhập
            hanghoaService.updateQuantity(nhap.getHanghoa().getHanghoaID(), -nhap.getSoLuongNhap());
            nhapRepository.deleteById(id);
        } else {
            throw new RuntimeException("Không tìm thấy phiếu nhập với ID: " + id);
        }
    }

    // Tìm phiếu nhập theo hàng hóa
    public List<Nhap> getNhapByHanghoa(Hanghoa hanghoa) {
        return nhapRepository.findByHanghoa(hanghoa);
    }

    // Tìm phiếu nhập theo tên hàng hóa
    public List<Nhap> searchByTenHangHoa(String tenHangHoa) {
        return nhapRepository.findByTenHangHoaContainingIgnoreCase(tenHangHoa);
    }

    // Tìm phiếu nhập theo ngày
    public List<Nhap> getNhapByDate(LocalDate ngayNhap) {
        return nhapRepository.findByNgayNhap(ngayNhap);
    }

    // Tìm phiếu nhập trong khoảng thời gian
    public List<Nhap> getNhapBetweenDates(LocalDate startDate, LocalDate endDate) {
        return nhapRepository.findByNgayNhapBetween(startDate, endDate);
    }

    // Lấy lịch sử nhập hàng theo hàng hóa
    public List<Nhap> getImportHistoryByHanghoa(Hanghoa hanghoa) {
        return nhapRepository.findByHanghoaOrderByNgayNhapDesc(hanghoa);
    }

    // Tính tổng số lượng đã nhập của một hàng hóa
    public Integer getTotalImportedQuantity(Hanghoa hanghoa) {
        Integer total = nhapRepository.getTotalQuantityImportedByHanghoa(hanghoa);
        return total != null ? total : 0;
    }

    // Tính tổng chi phí nhập trong ngày
    public Double getTotalCostByDate(LocalDate ngayNhap) {
        Double total = nhapRepository.getTotalCostByDate(ngayNhap);
        return total != null ? total : 0.0;
    }

    // Tính tổng chi phí nhập trong khoảng thời gian
    public Double getTotalCostBetweenDates(LocalDate startDate, LocalDate endDate) {
        Double total = nhapRepository.getTotalCostBetweenDates(startDate, endDate);
        return total != null ? total : 0.0;
    }

    // Lấy báo cáo nhập hàng theo tháng
    public List<Nhap> getMonthlyReport(int year, int month) {
        return nhapRepository.findByYearAndMonth(year, month);
    }

    // Tính tổng chi phí nhập trong tháng
    public Double getMonthlyCost(int year, int month) {
        List<Nhap> monthlyImports = getMonthlyReport(year, month);
        return monthlyImports.stream()
                .mapToDouble(nhap -> nhap.getSoLuongNhap() * nhap.getGiaNhap())
                .sum();
    }

    // Tìm kiếm phiếu nhập
    public List<Nhap> search(String keyword) {
        return nhapRepository.findByTenHangHoaContainingIgnoreCase(keyword);
    }

    // Lưu phiếu nhập (tạo mới hoặc cập nhật)
    public Nhap save(Nhap nhap) {
        // Kiểm tra hàng hóa tồn tại
        if (nhap.getHanghoa() == null) {
            throw new RuntimeException("Hàng hóa không được để trống");
        }

        // Nếu là phiếu nhập mới
        if (nhap.getId() == null) {
            return createNhap(nhap);
        } else {
            // Cập nhật phiếu nhập hiện có
            return updateNhap(nhap.getId(), nhap);
        }
    }
    
    // Xóa phiếu nhập theo ID
    public void deleteById(Long id) {
        deleteNhap(id);
    }
}
