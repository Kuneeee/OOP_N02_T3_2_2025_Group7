package com.taphoa.appbanhang.repository;

import com.taphoa.appbanhang.entity.XuatBan;
import com.taphoa.appbanhang.entity.Hanghoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface XuatBanRepository extends JpaRepository<XuatBan, Long> {
    
    // Tìm theo hàng hóa
    List<XuatBan> findByHanghoa(Hanghoa hanghoa);
    
    // Tìm theo tên hàng hóa
    List<XuatBan> findByTenHangHoaContainingIgnoreCase(String tenHangHoa);
    
    // Tìm theo ngày bán
    List<XuatBan> findByNgayBan(LocalDate ngayBan);
    
    // Tìm theo khoảng ngày
    List<XuatBan> findByNgayBanBetween(LocalDate startDate, LocalDate endDate);
    
    // Tìm theo hàng hóa và khoảng ngày
    List<XuatBan> findByHanghoaAndNgayBanBetween(Hanghoa hanghoa, LocalDate startDate, LocalDate endDate);
    
    // Tính tổng số lượng bán theo hàng hóa
    @Query("SELECT SUM(x.soLuongBan) FROM XuatBan x WHERE x.hanghoa = :hanghoa")
    Integer getTotalQuantitySoldByHanghoa(@Param("hanghoa") Hanghoa hanghoa);
    
    // Tính tổng doanh thu theo ngày
    @Query("SELECT SUM(x.soLuongBan * x.giaBan) FROM XuatBan x WHERE x.ngayBan = :ngayBan")
    Double getTotalRevenueByDate(@Param("ngayBan") LocalDate ngayBan);
    
    // Tính tổng doanh thu trong khoảng thời gian
    @Query("SELECT SUM(x.soLuongBan * x.giaBan) FROM XuatBan x WHERE x.ngayBan BETWEEN :startDate AND :endDate")
    Double getTotalRevenueBetweenDates(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    
    // Lấy lịch sử bán hàng theo hàng hóa (sắp xếp theo ngày mới nhất)
    List<XuatBan> findByHanghoaOrderByNgayBanDesc(Hanghoa hanghoa);
    
    // Lấy báo cáo bán hàng theo tháng
    @Query("SELECT x FROM XuatBan x WHERE YEAR(x.ngayBan) = :year AND MONTH(x.ngayBan) = :month ORDER BY x.ngayBan DESC")
    List<XuatBan> findByYearAndMonth(@Param("year") int year, @Param("month") int month);
    
    // Lấy top sản phẩm bán chạy
    @Query("SELECT x.hanghoa, SUM(x.soLuongBan) as totalSold FROM XuatBan x " +
           "WHERE x.ngayBan BETWEEN :startDate AND :endDate " +
           "GROUP BY x.hanghoa ORDER BY totalSold DESC")
    List<Object[]> getTopSellingProducts(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    
    // Lấy doanh thu theo hàng hóa
    @Query("SELECT x.hanghoa, SUM(x.soLuongBan * x.giaBan) as revenue FROM XuatBan x " +
           "WHERE x.ngayBan BETWEEN :startDate AND :endDate " +
           "GROUP BY x.hanghoa ORDER BY revenue DESC")
    List<Object[]> getRevenueByProduct(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    
    // Tìm theo tên hàng hóa hoặc thông tin hàng hóa
    @Query("SELECT x FROM XuatBan x WHERE x.tenHangHoa LIKE %:keyword% OR x.hanghoa.tenHangHoa LIKE %:keyword% OR x.hanghoa.loaiHangHoa LIKE %:keyword%")
    List<XuatBan> findByTenHangHoaContainingIgnoreCaseOrHanghoa_TenHangHoaContainingIgnoreCase(@Param("keyword") String keyword);
}
