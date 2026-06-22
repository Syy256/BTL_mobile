package com.example.watchstoreapp.model;

public class NguoiDung {
    private int id;
    private String hoTen;
    private String sdt;
    private String email;
    private String matKhau;
    private String vaiTro; // "admin" / "user"
    private String diaChi;
    private String ngayTao;

    public NguoiDung() {}

    public NguoiDung(String hoTen, String sdt, String email, String matKhau,
                     String vaiTro, String diaChi) {
        this.hoTen = hoTen;
        this.sdt = sdt;
        this.email = email;
        this.matKhau = matKhau;
        this.vaiTro = vaiTro;
        this.diaChi = diaChi;
    }

    public boolean isAdmin() { return "admin".equals(vaiTro); }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getHoTen() { return hoTen; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }
    public String getSdt() { return sdt; }
    public void setSdt(String sdt) { this.sdt = sdt; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getMatKhau() { return matKhau; }
    public void setMatKhau(String matKhau) { this.matKhau = matKhau; }
    public String getVaiTro() { return vaiTro; }
    public void setVaiTro(String vaiTro) { this.vaiTro = vaiTro; }
    public String getDiaChi() { return diaChi; }
    public void setDiaChi(String diaChi) { this.diaChi = diaChi; }
    public String getNgayTao() { return ngayTao; }
    public void setNgayTao(String ngayTao) { this.ngayTao = ngayTao; }
}
