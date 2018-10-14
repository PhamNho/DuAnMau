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
    private final DatabaseHelper  databaseHelper;

    public BillDetailDAO(Context context) {
        databaseHelper = new DatabaseHelper(context);

    }

    public long insertBillDetail(String billID,String bookID,String Quantity) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TB_COLUMN_BILL_ID,billID );
        contentValues.put(TB_COLUMN_BOOK_ID,bookID );
        contentValues.put(TB_COLUMN_QUANTITY,Quantity);
        long result = db.insert(BILL_DETAIL_TABLE, null, contentValues);
        if (Constant.isDEBUG) Log.e("insertBillDetail", "insertBillDetail ID : " +result);
        db.close();
        return result;
    }
    public long deleteBillDetail(int billDetailID) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        long result = db.delete(BILL_DETAIL_TABLE, TB_COLUMN_BILL_DETAIL_ID + " = ?",
                new String[]{String.valueOf(billDetailID)});
        db.close();
        return result;

    }
    public long updateBillDetail(int billDetailID,String billID,String bookID,String Quantity) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TB_COLUMN_BILL_ID,billID );
        contentValues.put(TB_COLUMN_BOOK_ID,bookID );
        contentValues.put(TB_COLUMN_QUANTITY,Quantity);
        return db.update(BILL_DETAIL_TABLE, contentValues, TB_COLUMN_BILL_DETAIL_ID + " = ?",
                new String[]{String.valueOf(billDetailID)});
    }
    public List<BillDetail> getAllBillDetailsByBillID(String ID) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        List<BillDetail> billDetails = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + BILL_DETAIL_TABLE+" WHERE "+TB_COLUMN_BILL_ID+ " = ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{ID});
        if (cursor.moveToFirst()) {
            do {
                Integer billDetailID = cursor.getInt(cursor.getColumnIndex(TB_COLUMN_BILL_DETAIL_ID));
                String billID = cursor.getString(cursor.getColumnIndex(TB_COLUMN_BILL_ID));
                String quantity = cursor.getString(cursor.getColumnIndex(TB_COLUMN_QUANTITY));
                String bookID = cursor.getString(cursor.getColumnIndex(TB_COLUMN_BOOK_ID));
                BillDetail billDetail = new BillDetail();
                billDetail.setBillDetailID(billDetailID);
                billDetail.setBillID(billID);
                billDetail.setBookID(bookID);
                billDetail.setQuantity(quantity);
                billDetails.add(billDetail);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return billDetails;
    }
    public List<BillDetail> getAllBillDetailsByBookID(String ID) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        List<BillDetail> billDetails = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + BILL_DETAIL_TABLE+" WHERE "+TB_COLUMN_BOOK_ID+ " = ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{ID});
        if (cursor.moveToFirst()) {
            do {
                Integer billDetailID = cursor.getInt(cursor.getColumnIndex(TB_COLUMN_BILL_DETAIL_ID));
                String billID = cursor.getString(cursor.getColumnIndex(TB_COLUMN_BILL_ID));
                String quantity = cursor.getString(cursor.getColumnIndex(TB_COLUMN_QUANTITY));
                String bookID = cursor.getString(cursor.getColumnIndex(TB_COLUMN_BOOK_ID));
                BillDetail billDetail = new BillDetail();
                billDetail.setBillDetailID(billDetailID);
                billDetail.setBillID(billID);
                billDetail.setBookID(bookID);
                billDetail.setQuantity(quantity);
                billDetails.add(billDetail);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return billDetails;
    }
}