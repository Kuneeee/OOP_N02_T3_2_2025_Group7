package com.taphoa.appbanhang.service;

import com.taphoa.appbanhang.entity.XuatBan;
import com.taphoa.appbanhang.entity.Hanghoa;
import com.taphoa.appbanhang.repository.XuatBanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class XuatBanService {

    @Autowired
    private XuatBanRepository xuatBanRepository;

    @Autowired
    private HanghoaService hanghoaService;

    // Lấy tất cả phiếu xuất bán
    public List<XuatBan> findAll() {
        return xuatBanRepository.findAll();
    }
    
    public List<XuatBan> getAllXuatBan() {
        return findAll();
    }

    // Lấy phiếu xuất bán theo ID
    public Optional<XuatBan> findById(Long id) {
        return xuatBanRepository.findById(id);
    }
    
    public Optional<XuatBan> getXuatBanById(Long id) {
        return findById(id);
    }
    
    // Tìm kiếm phiếu xuất bán
    public List<XuatBan> search(String keyword) {
        return xuatBanRepository.findByTenHangHoaContainingIgnoreCaseOrHanghoa_TenHangHoaContainingIgnoreCase(keyword);
    }
    
    // Tạo phiếu xuất bán mới
    public XuatBan createXuatBan(XuatBan xuatBan) {
        // Kiểm tra hàng hóa tồn tại
        if (xuatBan.getHanghoa() == null) {
            throw new RuntimeException("Hàng hóa không được để trống");
        }

        // Kiểm tra số lượng hàng có đủ để xuất không
        if (!hanghoaService.hasEnoughQuantity(xuatBan.getHanghoa().getHanghoaID(), xuatBan.getSoLuongBan())) {
            throw new RuntimeException("Số lượng hàng hóa trong kho không đủ để xuất");
        }

        // Cập nhật tên hàng hóa từ entity
        xuatBan.setTenHangHoa(xuatBan.getHanghoa().getTenHangHoa());
        
        // Lưu phiếu xuất bán
        XuatBan savedXuatBan = xuatBanRepository.save(xuatBan);
        
        // Giảm số lượng hàng hóa trong kho
        hanghoaService.updateQuantity(xuatBan.getHanghoa().getHanghoaID(), -xuatBan.getSoLuongBan());
        
        return savedXuatBan;
    }

    // Cập nhật phiếu xuất bán
    public XuatBan updateXuatBan(Long id, XuatBan xuatBanDetails) {
        Optional<XuatBan> optionalXuatBan = xuatBanRepository.findById(id);
        if (optionalXuatBan.isPresent()) {
            XuatBan xuatBan = optionalXuatBan.get();
            
            // Hoàn tác số lượng cũ (trả lại kho)
            hanghoaService.updateQuantity(xuatBan.getHanghoa().getHanghoaID(), xuatBan.getSoLuongBan());
            
            // Kiểm tra số lượng mới có đủ để xuất không
            if (!hanghoaService.hasEnoughQuantity(xuatBanDetails.getHanghoa().getHanghoaID(), xuatBanDetails.getSoLuongBan())) {
                // Nếu không đủ, hoàn tác lại
                hanghoaService.updateQuantity(xuatBan.getHanghoa().getHanghoaID(), -xuatBan.getSoLuongBan());
                throw new RuntimeException("Số lượng hàng hóa trong kho không đủ để xuất");
            }
            
            // Cập nhật thông tin mới
            xuatBan.setHanghoa(xuatBanDetails.getHanghoa());
            xuatBan.setTenHangHoa(xuatBanDetails.getHanghoa().getTenHangHoa());
            xuatBan.setSoLuongBan(xuatBanDetails.getSoLuongBan());
            xuatBan.setGiaBan(xuatBanDetails.getGiaBan());
            xuatBan.setNgayBan(xuatBanDetails.getNgayBan());
            
            // Lưu và giảm số lượng mới
            XuatBan savedXuatBan = xuatBanRepository.save(xuatBan);
            hanghoaService.updateQuantity(xuatBan.getHanghoa().getHanghoaID(), -xuatBan.getSoLuongBan());
            
            return savedXuatBan;
        }
        throw new RuntimeException("Không tìm thấy phiếu xuất bán với ID: " + id);
    }

    // Lưu phiếu xuất bán (tạo mới hoặc cập nhật)
    public XuatBan save(XuatBan xuatBan) {
        // Kiểm tra hàng hóa tồn tại
        if (xuatBan.getHanghoa() == null) {
            throw new RuntimeException("Hàng hóa không được để trống");
        }

        // Nếu là phiếu xuất bán mới
        if (xuatBan.getId() == null) {
            return createXuatBan(xuatBan);
        } else {
            // Cập nhật phiếu xuất bán hiện có
            return updateXuatBan(xuatBan.getId(), xuatBan);
        }
    }
    
    // Xóa phiếu xuất bán theo ID
    public void deleteById(Long id) {
        Optional<XuatBan> xuatBanOpt = findById(id);
        if (xuatBanOpt.isPresent()) {
            XuatBan xuatBan = xuatBanOpt.get();
            // Cộng lại số lượng vào kho khi xóa phiếu xuất
            hanghoaService.updateQuantity(xuatBan.getHanghoa().getId(), xuatBan.getSoLuong());
            xuatBanRepository.deleteById(id);
        } else {
            throw new RuntimeException("Không tìm thấy phiếu xuất bán với ID: " + id);
        }
    }

    // Xóa phiếu xuất bán
    public void deleteXuatBan(Long id) {
        Optional<XuatBan> optionalXuatBan = xuatBanRepository.findById(id);
        if (optionalXuatBan.isPresent()) {
            XuatBan xuatBan = optionalXuatBan.get();
            // Hoàn tác số lượng đã xuất (trả lại kho)
            hanghoaService.updateQuantity(xuatBan.getHanghoa().getHanghoaID(), xuatBan.getSoLuongBan());
            xuatBanRepository.deleteById(id);
        } else {
            throw new RuntimeException("Không tìm thấy phiếu xuất bán với ID: " + id);
        }
    }

    // Tìm phiếu xuất bán theo hàng hóa
    public List<XuatBan> getXuatBanByHanghoa(Hanghoa hanghoa) {
        return xuatBanRepository.findByHanghoa(hanghoa);
    }

    // Tìm phiếu xuất bán theo tên hàng hóa
    public List<XuatBan> searchByTenHangHoa(String tenHangHoa) {
        return xuatBanRepository.findByTenHangHoaContainingIgnoreCase(tenHangHoa);
    }

    // Tìm phiếu xuất bán theo ngày
    public List<XuatBan> getXuatBanByDate(LocalDate ngayBan) {
        return xuatBanRepository.findByNgayBan(ngayBan);
    }

    // Tìm phiếu xuất bán trong khoảng thời gian
    public List<XuatBan> getXuatBanBetweenDates(LocalDate startDate, LocalDate endDate) {
        return xuatBanRepository.findByNgayBanBetween(startDate, endDate);
    }

    // Lấy lịch sử bán hàng theo hàng hóa
    public List<XuatBan> getSalesHistoryByHanghoa(Hanghoa hanghoa) {
        return xuatBanRepository.findByHanghoaOrderByNgayBanDesc(hanghoa);
    }

    // Tính tổng số lượng đã bán của một hàng hóa
    public Integer getTotalSoldQuantity(Hanghoa hanghoa) {
        Integer total = xuatBanRepository.getTotalQuantitySoldByHanghoa(hanghoa);
        return total != null ? total : 0;
    }

    // Tính tổng doanh thu trong ngày
    public Double getTotalRevenueByDate(LocalDate ngayBan) {
        Double total = xuatBanRepository.getTotalRevenueByDate(ngayBan);
        return total != null ? total : 0.0;
    }

    // Tính tổng doanh thu trong khoảng thời gian
    public Double getTotalRevenueBetweenDates(LocalDate startDate, LocalDate endDate) {
        Double total = xuatBanRepository.getTotalRevenueBetweenDates(startDate, endDate);
        return total != null ? total : 0.0;
    }

    // Lấy báo cáo bán hàng theo tháng
    public List<XuatBan> getMonthlyReport(int year, int month) {
        return xuatBanRepository.findByYearAndMonth(year, month);
    }

    // Tính tổng doanh thu trong tháng
    public Double getMonthlyRevenue(int year, int month) {
        List<XuatBan> monthlySales = getMonthlyReport(year, month);
        return monthlySales.stream()
                .mapToDouble(xuatBan -> xuatBan.getSoLuongBan() * xuatBan.getGiaBan())
                .sum();
    }

    // Lấy sản phẩm bán chạy nhất trong khoảng thời gian
    public List<Object[]> getTopSellingProducts(LocalDate startDate, LocalDate endDate) {
        return xuatBanRepository.getTopSellingProducts(startDate, endDate);
    }

    // Lấy doanh thu theo sản phẩm
    public List<Object[]> getRevenueByProduct(LocalDate startDate, LocalDate endDate) {
        return xuatBanRepository.getRevenueByProduct(startDate, endDate);
    }
}
