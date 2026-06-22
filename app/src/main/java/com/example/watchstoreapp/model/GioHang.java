package com.example.watchstoreapp.model;

public class GioHang {
    private int id;
    private int nguoiDungId;
    private int sanPhamId;
    private int soLuong;
    private String ngayThem;
    // Join fields
    private String tenSanPham;
    private double giaBan;
    private double giaKm;
    private String anhSanPham;
    private int soLuongTon;

    public GioHang() {}

    public GioHang(int nguoiDungId, int sanPhamId, int soLuong) {
        this.nguoiDungId = nguoiDungId;
        this.sanPhamId = sanPhamId;
        this.soLuong = soLuong;
    }

    public double getGiaHienTai() {
        if (giaKm > 0) return giaKm;
        return giaBan;
    }

    public double getTongTien() {
        return getGiaHienTai() * soLuong;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getNguoiDungId() { return nguoiDungId; }
    public void setNguoiDungId(int nguoiDungId) { this.nguoiDungId = nguoiDungId; }
    public int getSanPhamId() { return sanPhamId; }
    public void setSanPhamId(int sanPhamId) { this.sanPhamId = sanPhamId; }
    public int getSoLuong() { return soLuong; }
    public void setSoLuong(int soLuong) { this.soLuong = soLuong; }
    public String getNgayThem() { return ngayThem; }
    public void setNgayThem(String ngayThem) { this.ngayThem = ngayThem; }
    public String getTenSanPham() { return tenSanPham; }
    public void setTenSanPham(String tenSanPham) { this.tenSanPham = tenSanPham; }
    public double getGiaBan() { return giaBan; }
    public void setGiaBan(double giaBan) { this.giaBan = giaBan; }
    public double getGiaKm() { return giaKm; }
    public void setGiaKm(double giaKm) { this.giaKm = giaKm; }
    public String getAnhSanPham() { return anhSanPham; }
    public void setAnhSanPham(String anhSanPham) { this.anhSanPham = anhSanPham; }
    public int getSoLuongTon() { return soLuongTon; }
    public void setSoLuongTon(int soLuongTon) { this.soLuongTon = soLuongTon; }
}
