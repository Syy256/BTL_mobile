package com.example.watchstoreapp.model;

public class HoaDon {
    private int id;
    private String maHd;
    private int nguoiDungId;
    private String tenNguoiDung;
    private String ngayDat;
    private double tongTien;
    private double giamGia;
    private double thanhTien;
    private String hinhThucTt;
    private String trangThai;
    private String diaChiGiao;
    private String ghiChu;

    public static final String TRANG_THAI_CHO = "Chờ xác nhận";
    public static final String TRANG_THAI_DANG_GIAO = "Đang giao";
    public static final String TRANG_THAI_HOAN_THANH = "Hoàn thành";
    public static final String TRANG_THAI_HUY = "Đã hủy";

    public HoaDon() {}

    public HoaDon(String maHd, int nguoiDungId, double tongTien, double giamGia,
                  double thanhTien, String hinhThucTt, String trangThai,
                  String diaChiGiao, String ghiChu) {
        this.maHd = maHd;
        this.nguoiDungId = nguoiDungId;
        this.tongTien = tongTien;
        this.giamGia = giamGia;
        this.thanhTien = thanhTien;
        this.hinhThucTt = hinhThucTt;
        this.trangThai = trangThai;
        this.diaChiGiao = diaChiGiao;
        this.ghiChu = ghiChu;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getMaHd() { return maHd; }
    public void setMaHd(String maHd) { this.maHd = maHd; }
    public int getNguoiDungId() { return nguoiDungId; }
    public void setNguoiDungId(int nguoiDungId) { this.nguoiDungId = nguoiDungId; }
    public String getTenNguoiDung() { return tenNguoiDung; }
    public void setTenNguoiDung(String tenNguoiDung) { this.tenNguoiDung = tenNguoiDung; }
    public String getNgayDat() { return ngayDat; }
    public void setNgayDat(String ngayDat) { this.ngayDat = ngayDat; }
    public double getTongTien() { return tongTien; }
    public void setTongTien(double tongTien) { this.tongTien = tongTien; }
    public double getGiamGia() { return giamGia; }
    public void setGiamGia(double giamGia) { this.giamGia = giamGia; }
    public double getThanhTien() { return thanhTien; }
    public void setThanhTien(double thanhTien) { this.thanhTien = thanhTien; }
    public String getHinhThucTt() { return hinhThucTt; }
    public void setHinhThucTt(String hinhThucTt) { this.hinhThucTt = hinhThucTt; }
    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }
    public String getDiaChiGiao() { return diaChiGiao; }
    public void setDiaChiGiao(String diaChiGiao) { this.diaChiGiao = diaChiGiao; }
    public String getGhiChu() { return ghiChu; }
    public void setGhiChu(String ghiChu) { this.ghiChu = ghiChu; }
}
