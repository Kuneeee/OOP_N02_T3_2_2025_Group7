package com.taphoa.appbanhang.repository;

import com.taphoa.appbanhang.entity.Nhap;
import com.taphoa.appbanhang.entity.Hanghoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface NhapRepository extends JpaRepository<Nhap, Long> {
    
    // Tìm theo hàng hóa
    List<Nhap> findByHanghoa(Hanghoa hanghoa);
    
    // Tìm theo tên hàng hóa
    @Query("SELECT n FROM Nhap n WHERE n.tenHangHoa LIKE %:keyword% OR n.hanghoa.tenHangHoa LIKE %:keyword% OR n.hanghoa.loaiHangHoa LIKE %:keyword%")
    List<Nhap> findByTenHangHoaContainingIgnoreCase(@Param("keyword") String keyword);
    
    // Tìm theo ngày nhập
    List<Nhap> findByNgayNhap(LocalDate ngayNhap);
    
    // Tìm theo khoảng ngày
    List<Nhap> findByNgayNhapBetween(LocalDate startDate, LocalDate endDate);
    
    // Tìm theo hàng hóa và khoảng ngày
    List<Nhap> findByHanghoaAndNgayNhapBetween(Hanghoa hanghoa, LocalDate startDate, LocalDate endDate);
    
    // Tính tổng số lượng nhập theo hàng hóa
    @Query("SELECT SUM(n.soLuongNhap) FROM Nhap n WHERE n.hanghoa = :hanghoa")
    Integer getTotalQuantityImportedByHanghoa(@Param("hanghoa") Hanghoa hanghoa);
    
    // Tính tổng chi phí nhập theo ngày
    @Query("SELECT SUM(n.soLuongNhap * n.giaNhap) FROM Nhap n WHERE n.ngayNhap = :ngayNhap")
    Double getTotalCostByDate(@Param("ngayNhap") LocalDate ngayNhap);
    
    // Tính tổng chi phí nhập trong khoảng thời gian
    @Query("SELECT SUM(n.soLuongNhap * n.giaNhap) FROM Nhap n WHERE n.ngayNhap BETWEEN :startDate AND :endDate")
    Double getTotalCostBetweenDates(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    
    // Lấy lịch sử nhập hàng theo hàng hóa (sắp xếp theo ngày mới nhất)
    List<Nhap> findByHanghoaOrderByNgayNhapDesc(Hanghoa hanghoa);
    
    // Lấy báo cáo nhập hàng theo tháng
    @Query("SELECT n FROM Nhap n WHERE YEAR(n.ngayNhap) = :year AND MONTH(n.ngayNhap) = :month ORDER BY n.ngayNhap DESC")
    List<Nhap> findByYearAndMonth(@Param("year") int year, @Param("month") int month);
}
