package com.nho_pc.nhopvph06243_ass.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.nho_pc.nhopvph06243_ass.database.DatabaseHelper;
import com.nho_pc.nhopvph06243_ass.listener.Constant;
import com.nho_pc.nhopvph06243_ass.model.Bill;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class BillDAO implements Constant {
    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;
    public static final String TABLE_NAME = "Bill";
    public static final String TAG = "BillDAO";
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

    public BillDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    //insert
    public int inserBill(Bill bill) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_BILL_ID, bill.getMaHoaDon());
        values.put(COLUMN_DATE_BILL, sdf.format(bill.getNgayMua()));
        try {
            if (db.insert(TABLE_NAME, null, values) == -1) {
                return -1;
            }
        } catch (Exception ex) {
            Log.e(TAG, ex.toString());
        }
        return 1;
    }

    //getAll
    public List<Bill> getAllBill() throws ParseException {
        List<Bill> dsBill = new ArrayList<>();
        Cursor c = db.query(TABLE_NAME, null, null, null, null, null, null);
        c.moveToFirst();
        while (c.isAfterLast() == false) {
            Bill bill = new Bill();
            bill.setMaHoaDon(c.getString(0));
            bill.setNgayMua(sdf.parse(c.getString(1)));
            dsBill.add(bill);
            c.moveToNext();
        }
        c.close();
        return dsBill;
    }

    //update
    public int updateBill(Bill bill) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_BILL_ID, bill.getMaHoaDon());
        values.put(COLUMN_DATE_BILL, sdf.format(bill.getNgayMua()));
        int result = db.update(TABLE_NAME, values, COLUMN_BILL_ID + "=?", new String[]{bill.getMaHoaDon()});
        if (result == 0) {
            return -1;
        }
        return 1;
    }

    //delete
    public int deleteBillByID(String billcode) {
        int result = db.delete(TABLE_NAME, COLUMN_BILL_ID + "=?", new String[]{billcode});
        if (result == 0)
            return -1;
        return 1;
    }
}