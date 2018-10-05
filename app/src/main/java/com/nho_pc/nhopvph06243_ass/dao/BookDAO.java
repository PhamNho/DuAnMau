package com.nho_pc.nhopvph06243_ass.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.nho_pc.nhopvph06243_ass.database.DatabaseHelper;
import com.nho_pc.nhopvph06243_ass.listener.Constant;
import com.nho_pc.nhopvph06243_ass.model.Book;

import java.util.ArrayList;
import java.util.List;

public class BookDAO implements Constant {
    private SQLiteDatabase db;
    private DatabaseHelper databaseHelper;
    public static final String TABLE_NAME = "book";
    public static final String TAG = "SachDAO";
    public BookDAO(Context context) {
        databaseHelper = new DatabaseHelper(context);
        db = databaseHelper.getWritableDatabase();
    }
    //insert
    public int inserBook(Book book){
        ContentValues values = new ContentValues();
        values.put(COLUMN_BOOK_ID,book.getMaSach());
        values.put(COLUMN_TYPE_ID,book.getMaTheLoai());
        values.put(COLUMN_BOOK_NAME,book.getTenSach());
        values.put(COLUMN_TAC_GIA,book.getTacGia());
        values.put(COLUMN_NXB,book.getNXB());
        values.put(COLUMN_GIA_BIA,book.getGiaBia());
        values.put(COLUMN_SO_LUONG,book.getSoLuong());
        if (checkPrimaryKey(book.getMaSach())){
            int result = db.update(TABLE_NAME,values,"bookID=?", new String[]{book.getMaSach()});
            if (result == 0){
                return -1;
            }
        }else {
            try {
                if (db.insert(TABLE_NAME, null, values) == -1) {
                    return -1;
                }
            } catch (Exception ex) {
                Log.e(TAG, ex.toString());
            }
        }
        return 1;
    }
    //getAll
    public List<Book> getAllBook(){
        List<Book> dsBook = new ArrayList<>();
        Cursor c = db.query(TABLE_NAME,null,null,null,null,null,null);
        c.moveToFirst();
        while (c.isAfterLast()==false){
            Book book = new Book();
            book.setMaSach(c.getString(0));
            book.setMaTheLoai(c.getString(1));
            book.setTenSach(c.getString(2));
            book.setTacGia(c.getString(3));
            book.setNXB(c.getString(4));
            book.setGiaBia(c.getString(5));
            book.setSoLuong(c.getString(6));
            dsBook.add(book);
            c.moveToNext();
        }
        c.close();
        return dsBook;
    }
    //update
    public int updateBook(Book book){
        ContentValues values = new ContentValues();
        values.put(COLUMN_BOOK_ID,book.getMaSach());
        values.put(COLUMN_TYPE_ID,book.getMaTheLoai());
        values.put(COLUMN_BOOK_NAME,book.getTenSach());
        values.put(COLUMN_TAC_GIA,book.getTacGia());
        values.put(COLUMN_NXB,book.getNXB());
        values.put(COLUMN_GIA_BIA,book.getGiaBia());
        values.put(COLUMN_SO_LUONG,book.getSoLuong());
        int result = db.update(TABLE_NAME,values,"bookID=?", new String[]{book.getMaSach()});
        if (result == 0){
            return -1;
        }
        return 1;
    }
    //delete
    public int deleteBookByID(String maSach){
        int result = db.delete(TABLE_NAME,"bookID=?",new String[]{maSach});
        if (result == 0)
            return -1;
        return 1;
    }
    //check
    public boolean checkPrimaryKey(String strPrimaryKey){
        //SELECT
        String[] columns = {COLUMN_BOOK_ID};
        //WHERE clause
        String selection = "bookID=?";
        //WHERE clause arguments
        String[] selectionArgs = {strPrimaryKey};
        Cursor c = null;
        try{
            c = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null,
                    null);
            c.moveToFirst();
            int i = c.getCount();
            c.close();
            if(i <= 0){
                return false;
            }
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
    //check
    public Book checkBook(String strPrimaryKey){
        Book book =new Book();
        //SELECT
        String[] columns = {COLUMN_BOOK_ID};
        //WHERE clause
        String selection = "bookID=?";
        //WHERE clause arguments
        String[] selectionArgs = {strPrimaryKey};
        Cursor c = null;
        try{c = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null,
                null);
            c.moveToFirst();
            while (c.isAfterLast()==false){
                book = new Book();
                book.setMaSach(c.getString(0));
                book.setMaTheLoai(c.getString(1));
                book.setTenSach(c.getString(2));
                book.setTacGia(c.getString(3));
                book.setNXB(c.getString(4));
                book.setGiaBia(c.getString(5));
                book.setSoLuong(c.getString(6));
                Log.d("//=====",book.toString());
                break;
            }
            c.close();
            return book;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    //getAll
    public Book getBookByID(String bookcode){
        Book book = null;
        //WHERE clause
        String selection = "bookID=?";
        //WHERE clause arguments
        String[] selectionArgs = {bookcode};
        Cursor c = db.query(TABLE_NAME,null,selection,selectionArgs,null,null,null);
        Log.d("getSachByID","===>"+ c.getCount());
        c.moveToFirst();
        while (c.isAfterLast()==false){
            book = new Book();
            book.setMaSach(c.getString(0));
            book.setMaTheLoai(c.getString(1));
            book.setTenSach(c.getString(2));
            book.setTacGia(c.getString(3));
            book.setNXB(c.getString(4));
            book.setGiaBia(c.getString(5));
            book.setSoLuong(c.getString(6));
            break;
        }
        c.close();
        return book;
    }
    //getAll
    public List<Book> getSachTop10(String month){
        List<Book> dsBook = new ArrayList<>();
        if (Integer.parseInt(month)<10){
            month = "0"+month;
        }
        String sSQL = "SELECT maSach, SUM(soLuong) as soluong FROM HoaDonChiTiet INNER JOIN HoaDon " + "ON HoaDon.maHoaDon = HoaDonChiTiet.maHoaDon WHERE strftime('%m',HoaDon.ngayMua) = '"+month+"' " + "GROUP BY maSach ORDER BY soluong DESC LIMIT 10";
        Cursor c = db.rawQuery(sSQL, null);
        c.moveToFirst();
        while (c.isAfterLast()==false){
            Log.d("//=====",c.getString(0));
            Book book = new Book();
            book.setMaSach(c.getString(0));
            book.setSoLuong(c.getString(1));
            book.setGiaBia("0");
            book.setMaTheLoai("");
            book.setTenSach("");
            book.setTacGia("");
            book.setNXB("");
            dsBook.add(book);
            c.moveToNext();
        }
        c.close();
        return dsBook;
    }
}