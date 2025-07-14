package com.taphoa.appbanhang.repository;

import com.taphoa.appbanhang.entity.DoanhThu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DoanhThuRepository extends JpaRepository<DoanhThu, Long> {
    
    // Tìm báo cáo theo ngày
    Optional<DoanhThu> findByNgayBaoCao(LocalDate ngayBaoCao);
    
    // Tìm báo cáo trong khoảng thời gian
    List<DoanhThu> findByNgayBaoCaoBetween(LocalDate startDate, LocalDate endDate);
    
    // Tìm báo cáo theo tháng
    @Query("SELECT d FROM DoanhThu d WHERE YEAR(d.ngayBaoCao) = :year AND MONTH(d.ngayBaoCao) = :month ORDER BY d.ngayBaoCao DESC")
    List<DoanhThu> findByYearAndMonth(@Param("year") int year, @Param("month") int month);
    
    // Tìm báo cáo theo năm
    @Query("SELECT d FROM DoanhThu d WHERE YEAR(d.ngayBaoCao) = :year ORDER BY d.ngayBaoCao DESC")
    List<DoanhThu> findByYear(@Param("year") int year);
    
    // Tính tổng doanh thu trong khoảng thời gian
    @Query("SELECT SUM(d.tongDoanhThu) FROM DoanhThu d WHERE d.ngayBaoCao BETWEEN :startDate AND :endDate")
    Double getTotalRevenueBetweenDates(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    
    // Tính tổng lợi nhuận trong khoảng thời gian
    @Query("SELECT SUM(d.loiNhuan) FROM DoanhThu d WHERE d.ngayBaoCao BETWEEN :startDate AND :endDate")
    Double getTotalProfitBetweenDates(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    
    // Tính tổng chi phí trong khoảng thời gian
    @Query("SELECT SUM(d.tongChiPhi) FROM DoanhThu d WHERE d.ngayBaoCao BETWEEN :startDate AND :endDate")
    Double getTotalCostBetweenDates(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    
    // Lấy báo cáo có lợi nhuận cao nhất
    @Query("SELECT d FROM DoanhThu d WHERE d.loiNhuan = (SELECT MAX(d2.loiNhuan) FROM DoanhThu d2)")
    Optional<DoanhThu> findHighestProfitReport();
    
    // Lấy báo cáo có doanh thu cao nhất
    @Query("SELECT d FROM DoanhThu d WHERE d.tongDoanhThu = (SELECT MAX(d2.tongDoanhThu) FROM DoanhThu d2)")
    Optional<DoanhThu> findHighestRevenueReport();
    
    // Lấy trung bình tỷ suất lợi nhuận
    @Query("SELECT AVG(d.tyXuatLoiNhuan) FROM DoanhThu d WHERE d.ngayBaoCao BETWEEN :startDate AND :endDate")
    Double getAverageProfitMargin(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    
    // Kiểm tra đã có báo cáo cho ngày này chưa
    boolean existsByNgayBaoCao(LocalDate ngayBaoCao);
    
    // Lấy báo cáo mới nhất
    @Query("SELECT d FROM DoanhThu d ORDER BY d.ngayBaoCao DESC LIMIT 1")
    Optional<DoanhThu> findLatestReport();
    
    // Tìm theo khoảng thời gian
    @Query("SELECT d FROM DoanhThu d WHERE d.ngayBaoCao BETWEEN :startDate AND :endDate ORDER BY d.ngayBaoCao DESC")
    List<DoanhThu> findByNgayTaoBetween(@Param("startDate") LocalDate startDate, 
                                       @Param("endDate") LocalDate endDate);
}
