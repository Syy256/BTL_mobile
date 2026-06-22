package com.example.watchstoreapp.model;

public class SanPham {

    private int id;
    private String maSp;
    private String ten;
    private int hangId;
    private String tenHang;

    private String gioiTinh;
    private String loaiMay;
    private String phanKhuc;

    // NEW FIELDS
    private String chatLieuVo;
    private String chatLieuDay;
    private String kinh;
    private int baoHanh;

    private double giaBan;
    private double giaKm;
    private String ngayKetThucKm;

    private int soLuong;
    private String anh;
    private String moTa;
    private int trangThai;

    public SanPham() {}

    public SanPham(String maSp, String ten, int hangId,
                   String gioiTinh, String loaiMay, String phanKhuc,
                   String chatLieuVo, String chatLieuDay, String kinh, int baoHanh,
                   double giaBan, double giaKm, String ngayKetThucKm,
                   int soLuong, String anh, String moTa, int trangThai) {

        this.maSp = maSp;
        this.ten = ten;
        this.hangId = hangId;
        this.gioiTinh = gioiTinh;
        this.loaiMay = loaiMay;
        this.phanKhuc = phanKhuc;

        this.chatLieuVo = chatLieuVo;
        this.chatLieuDay = chatLieuDay;
        this.kinh = kinh;
        this.baoHanh = baoHanh;

        this.giaBan = giaBan;
        this.giaKm = giaKm;
        this.ngayKetThucKm = ngayKetThucKm;

        this.soLuong = soLuong;
        this.anh = anh;
        this.moTa = moTa;
        this.trangThai = trangThai;
    }

    // ===== BUSINESS LOGIC =====
    public double getGiaHienTai() {
        return (giaKm > 0) ? giaKm : giaBan;
    }

    public boolean dangKhuyenMai() {
        return giaKm > 0;
    }

    // ===== GETTER/SETTER =====
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getMaSp() { return maSp; }
    public void setMaSp(String maSp) { this.maSp = maSp; }

    public String getTen() { return ten; }
    public void setTen(String ten) { this.ten = ten; }

    public int getHangId() { return hangId; }
    public void setHangId(int hangId) { this.hangId = hangId; }

    public String getTenHang() { return tenHang; }
    public void setTenHang(String tenHang) { this.tenHang = tenHang; }

    public String getGioiTinh() { return gioiTinh; }
    public void setGioiTinh(String gioiTinh) { this.gioiTinh = gioiTinh; }

    public String getLoaiMay() { return loaiMay; }
    public void setLoaiMay(String loaiMay) { this.loaiMay = loaiMay; }

    public String getPhanKhuc() { return phanKhuc; }
    public void setPhanKhuc(String phanKhuc) { this.phanKhuc = phanKhuc; }

    // NEW GETTER/SETTER
    public String getChatLieuVo() { return chatLieuVo; }
    public void setChatLieuVo(String chatLieuVo) { this.chatLieuVo = chatLieuVo; }

    public String getChatLieuDay() { return chatLieuDay; }
    public void setChatLieuDay(String chatLieuDay) { this.chatLieuDay = chatLieuDay; }

    public String getKinh() { return kinh; }
    public void setKinh(String kinh) { this.kinh = kinh; }

    public int getBaoHanh() { return baoHanh; }
    public void setBaoHanh(int baoHanh) { this.baoHanh = baoHanh; }

    public double getGiaBan() { return giaBan; }
    public void setGiaBan(double giaBan) { this.giaBan = giaBan; }

    public double getGiaKm() { return giaKm; }
    public void setGiaKm(double giaKm) { this.giaKm = giaKm; }

    public String getNgayKetThucKm() { return ngayKetThucKm; }
    public void setNgayKetThucKm(String ngayKetThucKm) { this.ngayKetThucKm = ngayKetThucKm; }

    public int getSoLuong() { return soLuong; }
    public void setSoLuong(int soLuong) { this.soLuong = soLuong; }

    public String getAnh() { return anh; }
    public void setAnh(String anh) { this.anh = anh; }

    public String getMoTa() { return moTa; }
    public void setMoTa(String moTa) { this.moTa = moTa; }

    public int getTrangThai() { return trangThai; }
    public void setTrangThai(int trangThai) { this.trangThai = trangThai; }
}