package com.taphoa.appbanhang.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.time.LocalDate;

@Entity
@Table(name = "doanhthu")
public class DoanhThu {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long doanhThuID;
    
    @NotNull(message = "Tỷ lệ xuất lợi nhuận không được để trống")
    @PositiveOrZero(message = "Tỷ lệ xuất lợi nhuận phải >= 0")
    @Column(nullable = false)
    private Double tyXuatLoiNhuan; // Tỷ lệ % lợi nhuận
    
    @NotNull(message = "Lợi nhuận không được để trống")
    @Column(nullable = false)
    private Double loiNhuan; // Số tiền lợi nhuận thực tế
    
    @Column(nullable = false)
    private LocalDate ngayBaoCao;
    
    @Column(nullable = false)
    private Double tongDoanhThu; // Tổng doanh thu trong ngày
    
    @Column(nullable = false)
    private Double tongChiPhi; // Tổng chi phí nhập hàng
    
    @Column(length = 500)
    private String ghiChu; // Ghi chú thêm
    
    // Constructors
    public DoanhThu() {
        this.ngayBaoCao = LocalDate.now();
    }
    
    public DoanhThu(Double tyXuatLoiNhuan, Double loiNhuan, LocalDate ngayBaoCao,
                    Double tongDoanhThu, Double tongChiPhi, String ghiChu) {
        this.tyXuatLoiNhuan = tyXuatLoiNhuan;
        this.loiNhuan = loiNhuan;
        this.ngayBaoCao = ngayBaoCao;
        this.tongDoanhThu = tongDoanhThu;
        this.tongChiPhi = tongChiPhi;
        this.ghiChu = ghiChu;
    }
    
    // Getters and Setters
    public Long getId() {
        return doanhThuID;
    }
    
    public void setId(Long id) {
        this.doanhThuID = id;
    }
    
    public Long getDoanhThuID() {
        return doanhThuID;
    }
    
    public void setDoanhThuID(Long doanhThuID) {
        this.doanhThuID = doanhThuID;
    }
    
    public Double getTyXuatLoiNhuan() {
        return tyXuatLoiNhuan;
    }
    
    public void setTyXuatLoiNhuan(Double tyXuatLoiNhuan) {
        this.tyXuatLoiNhuan = tyXuatLoiNhuan;
    }
    
    public Double getLoiNhuan() {
        return loiNhuan;
    }
    
    public void setLoiNhuan(Double loiNhuan) {
        this.loiNhuan = loiNhuan;
    }
    
    public LocalDate getNgayBaoCao() {
        return ngayBaoCao;
    }
    
    public void setNgayBaoCao(LocalDate ngayBaoCao) {
        this.ngayBaoCao = ngayBaoCao;
    }
    
    public Double getTongDoanhThu() {
        return tongDoanhThu;
    }
    
    public void setTongDoanhThu(Double tongDoanhThu) {
        this.tongDoanhThu = tongDoanhThu;
    }
    
    public Double getTongChiPhi() {
        return tongChiPhi;
    }
    
    public void setTongChiPhi(Double tongChiPhi) {
        this.tongChiPhi = tongChiPhi;
    }
    
    public String getGhiChu() {
        return ghiChu;
    }
    
    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }
    
    // Additional getters for UI compatibility
    public Hanghoa getHanghoa() {
        // DoanhThu represents aggregate data, not specific product data
        return null; // This entity represents summary data, not individual product sales
    }
    
    public Integer getSoLuongBan() {
        // Return 1 as default for summary records
        return 1; // Summary record represents one day's total
    }
    
    public Double getDoanhThu() {
        return tongDoanhThu;
    }
    
    public Double getGiaVon() {
        return tongChiPhi;
    }
    
    public LocalDate getNgayTao() {
        return ngayBaoCao;
    }
    
    // Business methods
    public void tinhToanDoanhThu() {
        if (tongDoanhThu != null && tongChiPhi != null) {
            this.loiNhuan = tongDoanhThu - tongChiPhi;
            if (tongChiPhi > 0) {
                this.tyXuatLoiNhuan = (loiNhuan / tongChiPhi) * 100;
            }
        }
    }
    
    @Override
    public String toString() {
        return "DoanhThu{" +
                "doanhThuID=" + doanhThuID +
                ", tyXuatLoiNhuan=" + tyXuatLoiNhuan +
                ", loiNhuan=" + loiNhuan +
                ", ngayBaoCao=" + ngayBaoCao +
                ", tongDoanhThu=" + tongDoanhThu +
                ", tongChiPhi=" + tongChiPhi +
                ", ghiChu='" + ghiChu + '\'' +
                '}';
    }
}
