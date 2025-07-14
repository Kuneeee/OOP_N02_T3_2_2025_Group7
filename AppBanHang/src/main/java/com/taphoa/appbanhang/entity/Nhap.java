package com.taphoa.appbanhang.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;

@Entity
@Table(name = "nhap")
public class Nhap {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long nhapID;
    
    @NotNull(message = "Hàng hóa không được để trống")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hanghoa_id", nullable = false)
    private Hanghoa hanghoa;
    
    @NotBlank(message = "Tên hàng hóa không được để trống")
    @Column(nullable = false)
    private String tenHangHoa;
    
    @NotNull(message = "Số lượng nhập không được để trống")
    @Positive(message = "Số lượng nhập phải lớn hơn 0")
    @Column(nullable = false)
    private Integer soLuongNhap;
    
    @NotNull(message = "Giá nhập không được để trống")
    @Positive(message = "Giá nhập phải lớn hơn 0")
    @Column(nullable = false)
    private Double giaNhap;
    
    @NotNull(message = "Ngày nhập không được để trống")
    @Column(nullable = false)
    private LocalDate ngayNhap;
    
    // Constructors
    public Nhap() {
        this.ngayNhap = LocalDate.now();
    }
    
    public Nhap(Hanghoa hanghoa, String tenHangHoa, Integer soLuongNhap, 
                Double giaNhap, LocalDate ngayNhap) {
        this.hanghoa = hanghoa;
        this.tenHangHoa = tenHangHoa;
        this.soLuongNhap = soLuongNhap;
        this.giaNhap = giaNhap;
        this.ngayNhap = ngayNhap;
    }
    
    // Getters and Setters
    public Long getNhapID() {
        return nhapID;
    }
    
    public void setNhapID(Long nhapID) {
        this.nhapID = nhapID;
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
    
    public Integer getSoLuongNhap() {
        return soLuongNhap;
    }
    
    public void setSoLuongNhap(Integer soLuongNhap) {
        this.soLuongNhap = soLuongNhap;
    }
    
    public Double getGiaNhap() {
        return giaNhap;
    }
    
    public void setGiaNhap(Double giaNhap) {
        this.giaNhap = giaNhap;
    }
    
    public LocalDate getNgayNhap() {
        return ngayNhap;
    }
    
    public void setNgayNhap(LocalDate ngayNhap) {
        this.ngayNhap = ngayNhap;
    }
    
    // Overloaded methods for different date types
    public void setNgayNhap(java.time.LocalDateTime ngayNhap) {
        this.ngayNhap = ngayNhap.toLocalDate();
    }
    
    // Getter/setter aliases for UI compatibility
    public Long getId() {
        return nhapID;
    }
    
    public void setId(Long id) {
        this.nhapID = id;
    }
    
    public Integer getSoLuong() {
        return soLuongNhap;
    }
    
    public void setSoLuong(Integer soLuong) {
        this.soLuongNhap = soLuong;
    }
    
    public java.time.LocalDateTime getNgayNhapDateTime() {
        return ngayNhap.atStartOfDay();
    }
    
    public void setNgayNhapDateTime(java.time.LocalDateTime ngayNhap) {
        this.ngayNhap = ngayNhap.toLocalDate();
    }
    
    public Double getThanhTien() {
        return soLuongNhap * giaNhap;
    }
    
    public void setThanhTien(Double thanhTien) {
        // Calculated field, no setter needed but provided for compatibility
    }
    
    public String getGhiChu() {
        return ""; // Default implementation
    }
    
    public void setGhiChu(String ghiChu) {
        // Default implementation
    }

    @Override
    public String toString() {
        return "Nhap{" +
                "nhapID=" + nhapID +
                ", tenHangHoa='" + tenHangHoa + '\'' +
                ", soLuongNhap=" + soLuongNhap +
                ", giaNhap=" + giaNhap +
                ", ngayNhap=" + ngayNhap +
                '}';
    }
}
