package com.nho_pc.nhopvph06243_ass.model;

public class BillDetail {
    private int billdetailID;
    private Bill hoaDon;
    private Book sach;
    private int soLuongMua;

    public BillDetail(int billdetailID, Bill hoaDon, Book sach, int soLuongMua) {
        this.billdetailID = billdetailID;
        this.hoaDon = hoaDon;
        this.sach = sach;
        this.soLuongMua = soLuongMua;
    }

    public BillDetail() {

    }

    public int getbilldetailID() {
        return billdetailID;
    }

    public void setMaHDCT(int billdetailID) {
        this.billdetailID = billdetailID;
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
}
