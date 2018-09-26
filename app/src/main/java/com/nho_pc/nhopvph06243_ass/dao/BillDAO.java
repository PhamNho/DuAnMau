package com.nho_pc.nhopvph06243_ass.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.nho_pc.nhopvph06243_ass.database.DatabaseHelper;
import com.nho_pc.nhopvph06243_ass.model.Bill;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class BillDAO {
    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;
    public static final String TABLE_NAME = "Bill";
    public static final String SQL_Bill="CREATE TABLE Bill (mahoadon textprimary key, ngaymua date);";
    public static final String TAG = "BillDAO";
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    public BillDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
    }
    //insert
    public int inserHoaDon(Bill bill){
        ContentValues values = new ContentValues();
        values.put("mahoadon",bill.getMaHoaDon());
        values.put("ngaymua",sdf.format(bill.getNgayMua()));
        try {
            if(db.insert(TABLE_NAME,null,values)== -1){
                return -1;
            }
        }catch (Exception ex){
            Log.e(TAG,ex.toString());
        }
        return 1;
    }
    //getAll
    public List<Bill> getAllHoaDon() throws ParseException {
        List<Bill> dsBill = new ArrayList<>();
        Cursor c = db.query(TABLE_NAME,null,null,null,null,null,null);
        c.moveToFirst();
        while (c.isAfterLast()==false){
            Bill bill = new Bill();
            bill.setMaHoaDon(c.getString(0));
            bill.setNgayMua(sdf.parse(c.getString(1)));
            dsBill.add(bill);
            Log.d("//=====",bill.toString());
            c.moveToNext();
        }
        c.close();
        return dsBill;
    }
    //update
    public int updateBill(Bill bill){
        ContentValues values = new ContentValues();
        values.put("mahoadon",bill.getMaHoaDon());
        values.put("ngaymua",bill.getNgayMua().toString());
        int result = db.update(TABLE_NAME,values,"mahoadon=?", new
                String[]{bill.getMaHoaDon()});
        if (result == 0){
            return -1;
        }
        return 1;
    }
    //delete
    public int deleteBillByID(String billcode){
        int result = db.delete(TABLE_NAME,"mahoadon=?",new String[]{billcode});
        if (result == 0)
            return -1;
        return 1;
    }
}