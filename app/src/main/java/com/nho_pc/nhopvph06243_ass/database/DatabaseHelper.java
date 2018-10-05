package com.nho_pc.nhopvph06243_ass.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.nho_pc.nhopvph06243_ass.dao.BillDAO;
//import com.nho_pc.nhopvph06243_ass.dao.BillDetailDAO;
import com.nho_pc.nhopvph06243_ass.dao.BillDetailDAO;
import com.nho_pc.nhopvph06243_ass.dao.BookDAO;
import com.nho_pc.nhopvph06243_ass.dao.BookTypeDAO;
import com.nho_pc.nhopvph06243_ass.dao.UserDAO;
import com.nho_pc.nhopvph06243_ass.listener.Constant;
import com.nho_pc.nhopvph06243_ass.model.Book;

public class DatabaseHelper extends SQLiteOpenHelper implements Constant{

    public static final String DATABASE_NAME = "dbBookManager";
    public static final int VERSION = 1;
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(UserDAO.CREATE_USER_TABLE);
        db.execSQL(BookTypeDAO.CREATE_BOOK_TYPE_TABLE);
        db.execSQL(BookDAO.CREATE_BOOK_TABLE);
        db.execSQL(BillDAO.CREATE_BILL_TABLE);
        db.execSQL(BillDAO.CREATE_BILLDETAIL_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop table if exists "+UserDAO.TABLE_NAME);
        db.execSQL("Drop table if exists "+BookTypeDAO.TABLE_NAME);
        db.execSQL("Drop table if exists "+BookDAO.TABLE_NAME);
        db.execSQL("Drop table if exists "+BillDAO.TABLE_NAME);
        db.execSQL("Drop table if exists "+BillDetailDAO.TABLE_NAME);
        onCreate(db);
    }
}
