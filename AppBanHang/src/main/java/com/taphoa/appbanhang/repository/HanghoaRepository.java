package com.taphoa.appbanhang.repository;

import com.taphoa.appbanhang.entity.Hanghoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HanghoaRepository extends JpaRepository<Hanghoa, Long> {
    
    // Tìm hàng hóa theo tên
    List<Hanghoa> findByTenHangHoaContainingIgnoreCase(String tenHangHoa);
    
    // Tìm hàng hóa theo loại
    List<Hanghoa> findByLoaiHangHoaContainingIgnoreCase(String loaiHangHoa);
    
    // Tìm hàng hóa theo nhà sản xuất
    List<Hanghoa> findByNhaSanXuatContainingIgnoreCase(String nhaSanXuat);
    
    // Tìm hàng hóa theo năm sản xuất
    List<Hanghoa> findByNamSanXuat(Integer namSanXuat);
    
    // Tìm hàng hóa có số lượng thấp hơn ngưỡng
    @Query("SELECT h FROM Hanghoa h WHERE h.soLuongHangHoa < :threshold")
    List<Hanghoa> findLowStockItems(@Param("threshold") Integer threshold);
    
    // Kiểm tra tên hàng hóa đã tồn tại
    boolean existsByTenHangHoaIgnoreCase(String tenHangHoa);
    
    // Tìm theo tên chính xác
    Optional<Hanghoa> findByTenHangHoaIgnoreCase(String tenHangHoa);
    
    // Tìm tất cả loại hàng hóa duy nhất
    @Query("SELECT DISTINCT h.loaiHangHoa FROM Hanghoa h ORDER BY h.loaiHangHoa")
    List<String> findDistinctLoaiHangHoa();
    
    // Tìm tất cả nhà sản xuất duy nhất
    @Query("SELECT DISTINCT h.nhaSanXuat FROM Hanghoa h ORDER BY h.nhaSanXuat")
    List<String> findDistinctNhaSanXuat();
}
