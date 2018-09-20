package com.nho_pc.nhopvph06243_ass.model;

public class DetailBill {
    private int maHDCT;
    private Bill hoaDon;
    private Book sach;
    private int soLuongMua;

    public DetailBill(int maHDCT, Bill hoaDon, Book sach, int soLuongMua) {
        this.maHDCT = maHDCT;
        this.hoaDon = hoaDon;
        this.sach = sach;
        this.soLuongMua = soLuongMua;
    }

    public int getMaHDCT() {
        return maHDCT;
    }

    public void setMaHDCT(int maHDCT) {
        this.maHDCT = maHDCT;
    }

    public Bill getHoaDon() {
        return hoaDon;
    }

    public void setHoaDon(Bill hoaDon) {
        this.hoaDon = hoaDon;
    }

    public Book getSach() {
        return sach;
    }

    public void setSach(Book sach) {
        this.sach = sach;
    }

    public int getSoLuongMua() {
        return soLuongMua;
    }

    public void setSoLuongMua(int soLuongMua) {
        this.soLuongMua = soLuongMua;
    }
    @Override
    public String toString() {
        return "HoaDonChiTiet{" +
                "maHDCT=" + maHDCT +
                ", hoaDon=" + hoaDon.toString() +
                ", sach=" + sach.toString() +
                ", soLuongMua=" + soLuongMua +
                '}';
    }
}
