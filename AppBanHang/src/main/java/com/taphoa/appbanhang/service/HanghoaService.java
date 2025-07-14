package com.taphoa.appbanhang.service;

import com.taphoa.appbanhang.entity.Hanghoa;
import com.taphoa.appbanhang.repository.HanghoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class HanghoaService {

    @Autowired
    private HanghoaRepository hanghoaRepository;

    // Lấy tất cả hàng hóa
    public List<Hanghoa> getAllHanghoa() {
        return hanghoaRepository.findAll();
    }
    
    // Alias method for UI compatibility
    public List<Hanghoa> findAll() {
        return getAllHanghoa();
    }

    // Lấy hàng hóa theo ID
    public Optional<Hanghoa> getHanghoaById(Long id) {
        return hanghoaRepository.findById(id);
    }

    // Lưu hàng hóa
    public Hanghoa saveHanghoa(Hanghoa hanghoa) {
        return hanghoaRepository.save(hanghoa);
    }

    // Cập nhật hàng hóa
    public Hanghoa updateHanghoa(Long id, Hanghoa hanghoaDetails) {
        Optional<Hanghoa> optionalHanghoa = hanghoaRepository.findById(id);
        if (optionalHanghoa.isPresent()) {
            Hanghoa hanghoa = optionalHanghoa.get();
            hanghoa.setTenHangHoa(hanghoaDetails.getTenHangHoa());
            hanghoa.setSoLuongHangHoa(hanghoaDetails.getSoLuongHangHoa());
            hanghoa.setNhaSanXuat(hanghoaDetails.getNhaSanXuat());
            hanghoa.setNamSanXuat(hanghoaDetails.getNamSanXuat());
            hanghoa.setLoaiHangHoa(hanghoaDetails.getLoaiHangHoa());
            return hanghoaRepository.save(hanghoa);
        }
        throw new RuntimeException("Không tìm thấy hàng hóa với ID: " + id);
    }

    // Xóa hàng hóa
    public void deleteHanghoa(Long id) {
        if (hanghoaRepository.existsById(id)) {
            hanghoaRepository.deleteById(id);
        } else {
            throw new RuntimeException("Không tìm thấy hàng hóa với ID: " + id);
        }
    }

    // Tìm kiếm hàng hóa theo tên
    public List<Hanghoa> searchByTenHangHoa(String tenHangHoa) {
        return hanghoaRepository.findByTenHangHoaContainingIgnoreCase(tenHangHoa);
    }

    // Tìm kiếm hàng hóa theo loại
    public List<Hanghoa> searchByLoaiHangHoa(String loaiHangHoa) {
        return hanghoaRepository.findByLoaiHangHoaContainingIgnoreCase(loaiHangHoa);
    }

    // Tìm kiếm hàng hóa theo nhà sản xuất
    public List<Hanghoa> searchByNhaSanXuat(String nhaSanXuat) {
        return hanghoaRepository.findByNhaSanXuatContainingIgnoreCase(nhaSanXuat);
    }

    // Tìm hàng hóa có số lượng thấp
    public List<Hanghoa> getLowStockItems(Integer threshold) {
        return hanghoaRepository.findLowStockItems(threshold);
    }

    // Kiểm tra tên hàng hóa đã tồn tại
    public boolean isNameExists(String tenHangHoa) {
        return hanghoaRepository.existsByTenHangHoaIgnoreCase(tenHangHoa);
    }

    // Tìm hàng hóa theo tên chính xác
    public Optional<Hanghoa> findByName(String tenHangHoa) {
        return hanghoaRepository.findByTenHangHoaIgnoreCase(tenHangHoa);
    }

    // Lấy danh sách loại hàng hóa
    public List<String> getAllLoaiHangHoa() {
        return hanghoaRepository.findDistinctLoaiHangHoa();
    }

    // Lấy danh sách nhà sản xuất
    public List<String> getAllNhaSanXuat() {
        return hanghoaRepository.findDistinctNhaSanXuat();
    }

    // Cập nhật số lượng hàng hóa (dùng khi nhập/xuất)
    public void updateQuantity(Long hanghoaId, Integer quantityChange) {
        Optional<Hanghoa> optionalHanghoa = hanghoaRepository.findById(hanghoaId);
        if (optionalHanghoa.isPresent()) {
            Hanghoa hanghoa = optionalHanghoa.get();
            int newQuantity = hanghoa.getSoLuongHangHoa() + quantityChange;
            if (newQuantity < 0) {
                throw new RuntimeException("Số lượng hàng hóa không đủ để xuất");
            }
            hanghoa.setSoLuongHangHoa(newQuantity);
            hanghoaRepository.save(hanghoa);
        } else {
            throw new RuntimeException("Không tìm thấy hàng hóa với ID: " + hanghoaId);
        }
    }

    // Kiểm tra số lượng có đủ để xuất không
    public boolean hasEnoughQuantity(Long hanghoaId, Integer requiredQuantity) {
        Optional<Hanghoa> optionalHanghoa = hanghoaRepository.findById(hanghoaId);
        if (optionalHanghoa.isPresent()) {
            return optionalHanghoa.get().getSoLuongHangHoa() >= requiredQuantity;
        }
        return false;
    }
}
