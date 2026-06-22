package com.example.watchstoreapp.model;

public class Hang {
    private int id;
    private String ten;
    private String xuatXu;
    private String moTa;
    private String logo;

    public Hang() {}

    public Hang(int id, String ten, String xuatXu, String moTa, String logo) {
        this.id = id;
        this.ten = ten;
        this.xuatXu = xuatXu;
        this.moTa = moTa;
        this.logo = logo;
    }

    public Hang(String ten, String xuatXu, String moTa, String logo) {
        this.ten = ten;
        this.xuatXu = xuatXu;
        this.moTa = moTa;
        this.logo = logo;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTen() { return ten; }
    public void setTen(String ten) { this.ten = ten; }
    public String getXuatXu() { return xuatXu; }
    public void setXuatXu(String xuatXu) { this.xuatXu = xuatXu; }
    public String getMoTa() { return moTa; }
    public void setMoTa(String moTa) { this.moTa = moTa; }
    public String getLogo() { return logo; }
    public void setLogo(String logo) { this.logo = logo; }

    @Override
    public String toString() { return ten; }
}
