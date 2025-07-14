package com.taphoa.appbanhang.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "hanghoa")
public class Hanghoa {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hanghoaID;
    
    @NotBlank(message = "Tên hàng hóa không được để trống")
    @Column(nullable = false)
    private String tenHangHoa;
    
    @NotNull(message = "Số lượng hàng hóa không được để trống")
    @Positive(message = "Số lượng hàng hóa phải lớn hơn 0")
    @Column(nullable = false)
    private Integer soLuongHangHoa;
    
    @NotBlank(message = "Nhà sản xuất không được để trống")
    @Column(nullable = false)
    private String nhaSanXuat;
    
    @NotNull(message = "Năm sản xuất không được để trống")
    @Column(nullable = false)
    private Integer namSanXuat;
    
    @NotBlank(message = "Loại hàng hóa không được để trống")
    @Column(nullable = false)
    private String loaiHangHoa;
    
    // Constructors
    public Hanghoa() {}
    
    public Hanghoa(String tenHangHoa, Integer soLuongHangHoa, String nhaSanXuat, 
                   Integer namSanXuat, String loaiHangHoa) {
        this.tenHangHoa = tenHangHoa;
        this.soLuongHangHoa = soLuongHangHoa;
        this.nhaSanXuat = nhaSanXuat;
        this.namSanXuat = namSanXuat;
        this.loaiHangHoa = loaiHangHoa;
    }
    
    // Getters and Setters
    public Long getHanghoaID() {
        return hanghoaID;
    }
    
    public void setHanghoaID(Long hanghoaID) {
        this.hanghoaID = hanghoaID;
    }
    
    public String getTenHangHoa() {
        return tenHangHoa;
    }
    
    public void setTenHangHoa(String tenHangHoa) {
        this.tenHangHoa = tenHangHoa;
    }
    
    public Integer getSoLuongHangHoa() {
        return soLuongHangHoa;
    }
    
    public void setSoLuongHangHoa(Integer soLuongHangHoa) {
        this.soLuongHangHoa = soLuongHangHoa;
    }
    
    public String getNhaSanXuat() {
        return nhaSanXuat;
    }
    
    public void setNhaSanXuat(String nhaSanXuat) {
        this.nhaSanXuat = nhaSanXuat;
    }
    
    public Integer getNamSanXuat() {
        return namSanXuat;
    }
    
    public void setNamSanXuat(Integer namSanXuat) {
        this.namSanXuat = namSanXuat;
    }
    
    public String getLoaiHangHoa() {
        return loaiHangHoa;
    }
    
    public void setLoaiHangHoa(String loaiHangHoa) {
        this.loaiHangHoa = loaiHangHoa;
    }
    
    // Getter/setter aliases for UI compatibility
    public Long getId() {
        return hanghoaID;
    }
    
    public void setId(Long id) {
        this.hanghoaID = id;
    }
    
    public String getMaHang() {
        return "HH" + String.format("%04d", hanghoaID != null ? hanghoaID : 0);
    }
    
    public String getTenHang() {
        return tenHangHoa;
    }
    
    public void setTenHang(String tenHang) {
        this.tenHangHoa = tenHang;
    }
    
    public Integer getSoLuongTon() {
        return soLuongHangHoa;
    }
    
    public void setSoLuongTon(Integer soLuongTon) {
        this.soLuongHangHoa = soLuongTon;
    }
    
    public Double getGiaBan() {
        return 0.0; // Default implementation - field not in original entity
    }
    
    public void setGiaBan(Double giaBan) {
        // Default implementation - field not in original entity
    }

    @Override
    public String toString() {
        return "Hanghoa{" +
                "hanghoaID=" + hanghoaID +
                ", tenHangHoa='" + tenHangHoa + '\'' +
                ", soLuongHangHoa=" + soLuongHangHoa +
                ", nhaSanXuat='" + nhaSanXuat + '\'' +
                ", namSanXuat=" + namSanXuat +
                ", loaiHangHoa='" + loaiHangHoa + '\'' +
                '}';
    }
}
