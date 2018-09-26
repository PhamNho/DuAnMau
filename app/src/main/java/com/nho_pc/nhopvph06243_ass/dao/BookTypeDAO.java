package com.nho_pc.nhopvph06243_ass.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.nho_pc.nhopvph06243_ass.database.DatabaseHelper;
import com.nho_pc.nhopvph06243_ass.model.BookType;

import java.util.ArrayList;
import java.util.List;

public class BookTypeDAO {
    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;
    public static final String TABLE_NAME = "TheLoai";
    public static final String SQL_BOOK_TYPE ="CREATE TABLE TheLoai (matheloai text primary key, tentheloai text, mota text, vitri int);";
    public static final String TAG = "TheLoaiDAO";

    public BookTypeDAO(Context context){
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
    }
    //insert
    public int inserTheLoai(BookType bookType){
        ContentValues values = new ContentValues();
        values.put("matheloai",bookType.getMaTheLoai());
        values.put("tentheloai",bookType.getTenTheLoai());
        values.put("mota",bookType.getMoTa());
        values.put("vitri",bookType.getViTri());
        try {
            if(db.insert(TABLE_NAME,null,values)== -1){
                return -1;
            }
        }catch (Exception ex){
            Log.e(TAG,ex.toString());
        }
        return 1;
    }
    // lấy ra tất cả thể loại
    public List<BookType> getAllBookType(){
        List<BookType> bookTypeList = new ArrayList<>();
        Cursor c = db.query(TABLE_NAME,null,null,null,null,null,null);
        c.moveToFirst();
        while (c.isAfterLast()==false){
            BookType bookType = new BookType();
            bookType.setMaTheLoai(c.getString(0));
            bookType.setTenTheLoai(c.getString(1));
            bookType.setMoTa(c.getString(2));
            bookType.setViTri(c.getInt(3));
            bookTypeList.add(bookType);
            Log.d("getAllBookType",bookType.toString());
            c.moveToNext();
        }
        c.close();
        return bookTypeList;
    }
    //update
    public int updateBookType(BookType bookType){
        ContentValues values = new ContentValues();
        values.put("matheloai",bookType.getMaTheLoai());
        values.put("tentheloai",bookType.getTenTheLoai());
        values.put("mota",bookType.getMoTa());
        values.put("vitri",bookType.getViTri());
        int result = db.update(TABLE_NAME,values,"matheloai=?", new
                String[]{bookType.getMaTheLoai()});
        if (result == 0){
            return -1;
        }
        return 1;
    }
    //delete
    public int deleteTheLoaiByID(String matheloai){
        int result = db.delete(TABLE_NAME,"matheloai=?",new String[]{matheloai});
        if (result == 0)
            return -1;
        return 1;
    }
}