package com.nho_pc.nhopvph06243_ass.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.nho_pc.nhopvph06243_ass.database.DatabaseHelper;
import com.nho_pc.nhopvph06243_ass.listener.Constant;
import com.nho_pc.nhopvph06243_ass.model.Bill;
import com.nho_pc.nhopvph06243_ass.model.BillDetail;
import com.nho_pc.nhopvph06243_ass.model.Book;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class BillDetailDAO implements Constant {
    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;
    public static final String TABLE_NAME = "HoaDonChiTiet";
    public static final String TAG = "HoaDonChiTiet";
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public BillDetailDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    //insert
    public int inserBillDetail(BillDetail billDetail) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_BILL_ID, billDetail.getHoaDon().getMaHoaDon());
        values.put(COLUMN_BOOK_ID, billDetail.getSach().getMaSach());
        values.put(COLUMN_SO_LUONG, billDetail.getSoLuongMua());
        try {
            if (db.insert(TABLE_NAME, null, values) == -1) {
                return -1;
            }
        } catch (Exception ex) {
            Log.e(TAG, ex.toString());
        }
        return 1;
    }

    //getAllBill
    public List<BillDetail> getAllBillDetail() {
        List<BillDetail> dsHoaDonChiTiet = new ArrayList<>();
        String sSQL = "SELECT maHDCT, HoaDon.maHoaDon,HoaDon.ngayMua, " + "Sach.maSach, Sach.maTheLoai, Sach.tenSach, Sach.tacGia, Sach.NXB, Sach.giaBia, " +
                "Sach.soLuong,HoaDonChiTiet.soLuong FROM HoaDonChiTiet INNER JOIN HoaDon " +
                "on HoaDonChiTiet.maHoaDon = HoaDon.maHoaDon INNER JOIN Sach on Sach.maSach = HoaDonChiTiet.maSach";
        Cursor c = db.rawQuery(sSQL, null);
        c.moveToFirst();
        try {
            while (c.isAfterLast() == false) {
                BillDetail billDetail = new BillDetail();
                billDetail.setMaHDCT(c.getInt(0));
                billDetail.setHoaDon(new Bill(c.getString(1), sdf.parse(c.getString(2))));
                billDetail.setSach(new Book(c.getString(3), c.getString(4), c.getString(5), c.getString(6), c.getString(7), c.getDouble(8), c.getInt(9)));
                billDetail.setSoLuongMua(c.getInt(10));
                dsHoaDonChiTiet.add(billDetail);
                c.moveToNext();
            }
            c.close();
        } catch (Exception e) {
            Log.d(TAG, e.toString());
        }
        return dsHoaDonChiTiet;
    }

    // lấy ra tất cả hóa đơn chi tiết
    public List<BillDetail> getAllBillDetailByID(String billID) {
        List<BillDetail> billDetailList = new ArrayList<>();
        String sSQL = "SELECT billdetailID, bill.billID,bill.datebill, " +
                "book.bookID, book.typeID, book.bookname, book.tacgia, book.nxb, book.giabia, " +
                "book.soluong,billdetail.somuongmua FROM billdetail INNER JOIN bill " +
                "on billdetail.billID = bill.billID INNER JOIN book on book.booID = billdetail.bookID where billdetail.billID='" + billID + "'";
        Cursor c = db.rawQuery(sSQL, null);
        c.moveToFirst();
        try {
            while (c.isAfterLast() == false) {
                BillDetail billDetail = new BillDetail();
                billDetail.setMaHDCT(c.getInt(0));
                billDetail.setHoaDon(new Bill(c.getString(1), sdf.parse(c.getString(2))));
                billDetail.setSach(new Book(c.getString(3), c.getString(4), c.getString(5), c.getString(6), c.getString(7), c.getDouble(8), c.getInt(9)));
                billDetail.setSoLuongMua(c.getInt(10));
                billDetailList.add(billDetail);
                c.moveToNext();
            }
            c.close();
        } catch (Exception e) {
            Log.d(TAG, e.toString());
        }
        return billDetailList;
    }

    //update
    public int updateHoaDonChiTiet(BillDetail billDetail) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_BILLDETAIL_ID, billDetail.getbilldetailID());
        values.put(COLUMN_BILL_ID, billDetail.getHoaDon().getMaHoaDon());
        values.put(COLUMN_BOOK_ID, billDetail.getSach().getMaSach());
        values.put(COLUMN_SO_LUONG, billDetail.getSoLuongMua());
        int result = db.update(TABLE_NAME, values, COLUMN_BILLDETAIL_ID+"=?", new
                String[]{String.valueOf(billDetail.getbilldetailID())});
        if (result == 0) {
            return -1;
        }
        return 1;
    }

    //delete
    public int deleteHoaDonChiTietByID(int maHDCT) {
        int result = db.delete(TABLE_NAME, COLUMN_BILLDETAIL_ID+"=?", new String[]{String.valueOf(maHDCT)});
        if (result == 0)
            return -1;
        return 1;
    }

    //check
    public boolean checkHoaDon(String billID) {
        //SELECT
        String[] columns = {COLUMN_BILL_ID};
        //WHERE clause
        String selection = COLUMN_BILLDETAIL_ID+"=?";
        //WHERE clause arguments
        String[] selectionArgs = {billID};
        Cursor c = null;
        try {
            c = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null,
                    null);
            c.moveToFirst();
            int i = c.getCount();
            c.close();
            if (i <= 0) {
                return false;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public double getDoanhThuTheoNgay() {
        double doanhThu = 0;
        String sSQL = "SELECT SUM(tongtien) from (SELECT SUM(book.giabia * billdetail.soluongmua)as 'tongtien' " +
        "FROM bill INNER JOIN billdetail on bill.billID = billdetail.billID" +
        "INNER JOIN book on billdetail.bookID = book.bookID where bill.datebill = date('now') GROUP BY billdetail.bookID)tmp ";
        Cursor c = db.rawQuery(sSQL, null);
        c.moveToFirst();
        while (c.isAfterLast() == false) {
            doanhThu = c.getDouble(0);
            c.moveToNext();
        }
        c.close();
        return doanhThu;
    }

    public double getDoanhThuTheoThang() {
        double doanhThu = 0;
        String sSQL = "SELECT SUM(tongtien) from (SELECT SUM(book.giaBia * billdetail.soluongmua) as 'tongtien' " +
        "FROM bill INNER JOIN billdetail on bill.billID = billdetail.billID" +
        "INNER JOIN Sach on HoaDonChiTiet.maSach = Sach.maSach where strftime('%m',HoaDon.ngayMua) = strftime('%m','now') GROUP BY HoaDonChiTiet.maSach)tmp";
        Cursor c = db.rawQuery(sSQL, null);
        c.moveToFirst();
        while (c.isAfterLast() == false) {
            doanhThu = c.getDouble(0);
            c.moveToNext();
        }
        c.close();
        return doanhThu;
    }

    public double getDoanhThuTheoNam() {
        double doanhThu = 0;
        String sSQL = "SELECT SUM(tongtien) from (SELECT SUM(Sach.giaBia * HoaDonChiTiet.soLuong) as 'tongtien' " +
                "FROM HoaDon INNER JOIN HoaDonChiTiet on HoaDon.maHoaDon = HoaDonChiTiet.maHoaDon " +
                "INNER JOIN Sach on HoaDonChiTiet.maSach = Sach.maSach where strftime('%Y',HoaDon.ngayMua) = strftime('%Y','now') GROUP BY HoaDonChiTiet.maSach)tmp";
        Cursor c = db.rawQuery(sSQL, null);
        c.moveToFirst();
        while (c.isAfterLast() == false) {
            doanhThu = c.getDouble(0);
            c.moveToNext();
        }
        c.close();
        return doanhThu;
    }
}
