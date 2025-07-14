package com.taphoa.appbanhang.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;

@Entity
@Table(name = "xuatban")
public class XuatBan {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long xuatBanID;
    
    @NotNull(message = "Hàng hóa không được để trống")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hanghoa_id", nullable = false)
    private Hanghoa hanghoa;
    
    @NotBlank(message = "Tên hàng hóa không được để trống")
    @Column(nullable = false)
    private String tenHangHoa;
    
    @NotNull(message = "Số lượng bán không được để trống")
    @Positive(message = "Số lượng bán phải lớn hơn 0")
    @Column(nullable = false)
    private Integer soLuongBan;
    
    @NotNull(message = "Giá bán không được để trống")
    @Positive(message = "Giá bán phải lớn hơn 0")
    @Column(nullable = false)
    private Double giaBan;
    
    @Column(nullable = false)
    private LocalDate ngayBan;
    
    // Constructors
    public XuatBan() {
        this.ngayBan = LocalDate.now();
    }
    
    public XuatBan(Hanghoa hanghoa, String tenHangHoa, Integer soLuongBan, 
                   Double giaBan, LocalDate ngayBan) {
        this.hanghoa = hanghoa;
        this.tenHangHoa = tenHangHoa;
        this.soLuongBan = soLuongBan;
        this.giaBan = giaBan;
        this.ngayBan = ngayBan;
    }
    
    // Getters and Setters
    public Long getXuatBanID() {
        return xuatBanID;
    }
    
    public void setXuatBanID(Long xuatBanID) {
        this.xuatBanID = xuatBanID;
    }
    
    public Hanghoa getHanghoa() {
        return hanghoa;
    }
    
    public void setHanghoa(Hanghoa hanghoa) {
        this.hanghoa = hanghoa;
        if (hanghoa != null) {
            this.tenHangHoa = hanghoa.getTenHangHoa();
        }
    }
    
    public String getTenHangHoa() {
        return tenHangHoa;
    }
    
    public void setTenHangHoa(String tenHangHoa) {
        this.tenHangHoa = tenHangHoa;
    }
    
    public Integer getSoLuongBan() {
        return soLuongBan;
    }
    
    public void setSoLuongBan(Integer soLuongBan) {
        this.soLuongBan = soLuongBan;
    }
    
    public Double getGiaBan() {
        return giaBan;
    }
    
    public void setGiaBan(Double giaBan) {
        this.giaBan = giaBan;
    }
    
    public LocalDate getNgayBan() {
        return ngayBan;
    }
    
    public void setNgayBan(LocalDate ngayBan) {
        this.ngayBan = ngayBan;
    }
    
    // Overloaded method for LocalDateTime compatibility
    public java.time.LocalDateTime getNgayBanDateTime() {
        return ngayBan.atStartOfDay();
    }
    
    public void setNgayBanDateTime(java.time.LocalDateTime ngayBan) {
        this.ngayBan = ngayBan.toLocalDate();
    }
    
    // Calculated field for revenue
    public Double getDoanhThu() {
        return soLuongBan * giaBan;
    }
    
    // Getter/setter aliases for UI compatibility
    public Long getId() {
        return xuatBanID;
    }
    
    public void setId(Long id) {
        this.xuatBanID = id;
    }
    
    public Integer getSoLuong() {
        return soLuongBan;
    }
    
    public void setSoLuong(Integer soLuong) {
        this.soLuongBan = soLuong;
    }
    
    public Double getThanhTien() {
        return soLuongBan * giaBan;
    }
    
    public void setThanhTien(Double thanhTien) {
        // Calculated field, no setter needed but provided for compatibility
    }
    
    public String getKhachHang() {
        return ""; // Default implementation - field not in original entity
    }
    
    public void setKhachHang(String khachHang) {
        // Default implementation - field not in original entity
    }
    
    public String getGhiChu() {
        return ""; // Default implementation - field not in original entity
    }
    
    public void setGhiChu(String ghiChu) {
        // Default implementation - field not in original entity
    }

    @Override
    public String toString() {
        return "XuatBan{" +
                "xuatBanID=" + xuatBanID +
                ", tenHangHoa='" + tenHangHoa + '\'' +
                ", soLuongBan=" + soLuongBan +
                ", giaBan=" + giaBan +
                ", ngayBan=" + ngayBan +
                '}';
    }
}
