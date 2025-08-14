package service;

import entity.KhachHang;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.CustomerRepositoryJPA;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    
    @Autowired
    private CustomerRepositoryJPA customerRepository;
    
    // CREATE - Tạo mới khách hàng
    public KhachHang createCustomer(KhachHang khachHang) {
        if (khachHang.getKhachHangId() == null || khachHang.getKhachHangId().isEmpty()) {
            khachHang.setKhachHangId(taoMaKhachHangTuDong());
        }
        return customerRepository.save(khachHang);
    }
    
    // READ - Lấy tất cả khách hàng
    public List<KhachHang> getAllCustomers() {
        return customerRepository.findAll();
    }
    
    // READ - Lấy khách hàng theo ID
    public Optional<KhachHang> getCustomerById(String id) {
        return customerRepository.findById(id);
    }
    
    // UPDATE - Cập nhật khách hàng
    public KhachHang updateCustomer(String id, KhachHang khachHangDetails) {
        return customerRepository.findById(id)
                .map(khachHang -> {
                    khachHang.setTenKhachHang(khachHangDetails.getTenKhachHang());
                    khachHang.setSoDienThoai(khachHangDetails.getSoDienThoai());
                    khachHang.setEmail(khachHangDetails.getEmail());
                    khachHang.setDiaChi(khachHangDetails.getDiaChi());
                    khachHang.setLoaiKhachHang(khachHangDetails.getLoaiKhachHang());
                    return customerRepository.save(khachHang);
                })
                .orElse(null);
    }
    
    // DELETE - Xóa khách hàng
    public boolean deleteCustomer(String id) {
        if (customerRepository.existsById(id)) {
            customerRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    // UTILITY - Tạo mã tự động
    private String taoMaKhachHangTuDong() {
        return customerRepository.findAll().stream()
                .filter(kh -> kh.getKhachHangId() != null && !kh.getKhachHangId().isEmpty())
                .map(kh -> kh.getKhachHangId())
                .filter(id -> id.startsWith("KH"))
                .map(id -> {
                    try {
                        return Integer.parseInt(id.substring(2));
                    } catch (NumberFormatException e) {
                        return 0;
                    }
                })
                .max(Integer::compareTo)
                .map(max -> "KH" + String.format("%03d", max + 1))
                .orElse("KH001");
    }
}
