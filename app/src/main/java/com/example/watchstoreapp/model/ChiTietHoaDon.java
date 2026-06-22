package com.example.watchstoreapp.model;

public class ChiTietHoaDon {
    private int id;
    private int hoaDonId;
    private int sanPhamId;
    private int soLuong;
    private double giaBan;
    // Join fields
    private String tenSanPham;
    private String anhSanPham;
    private String tenHang;

    public ChiTietHoaDon() {}

    public ChiTietHoaDon(int hoaDonId, int sanPhamId, int soLuong, double giaBan) {
        this.hoaDonId = hoaDonId;
        this.sanPhamId = sanPhamId;
        this.soLuong = soLuong;
        this.giaBan = giaBan;
    }

    public double getThanhTien() { return giaBan * soLuong; }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getHoaDonId() { return hoaDonId; }
    public void setHoaDonId(int hoaDonId) { this.hoaDonId = hoaDonId; }
    public int getSanPhamId() { return sanPhamId; }
    public void setSanPhamId(int sanPhamId) { this.sanPhamId = sanPhamId; }
    public int getSoLuong() { return soLuong; }
    public void setSoLuong(int soLuong) { this.soLuong = soLuong; }
    public double getGiaBan() { return giaBan; }
    public void setGiaBan(double giaBan) { this.giaBan = giaBan; }
    public String getTenSanPham() { return tenSanPham; }
    public void setTenSanPham(String tenSanPham) { this.tenSanPham = tenSanPham; }
    public String getAnhSanPham() { return anhSanPham; }
    public void setAnhSanPham(String anhSanPham) { this.anhSanPham = anhSanPham; }
    public String getTenHang() { return tenHang; }
    public void setTenHang(String tenHang) { this.tenHang = tenHang; }
}
