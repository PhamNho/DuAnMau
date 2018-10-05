package com.nho_pc.nhopvph06243_ass.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.nho_pc.nhopvph06243_ass.database.DatabaseHelper;
import com.nho_pc.nhopvph06243_ass.listener.Constant;
import com.nho_pc.nhopvph06243_ass.model.BookType;

import java.util.ArrayList;
import java.util.List;

public class BookTypeDAO implements Constant {
    private SQLiteDatabase db;
    private DatabaseHelper databaseHelper;
    public static final String TABLE_NAME = "BookType";
    public static final String TAG = "BookTypeDAO";

    public BookTypeDAO(Context context) {
        databaseHelper = new DatabaseHelper(context);
        db = databaseHelper.getWritableDatabase();
    }
    //insert
    public int inserBookType(BookType bookType){
        ContentValues values = new ContentValues();
        values.put(COLUMN_TYPE_ID,bookType.getTypeID());
        values.put(COLUMN_TYPE_NAME,bookType.getTypeName());
        values.put(COLUMN_DESCRIPTION,bookType.getDescription());
        values.put(COLUMN_POSITION,bookType.getPosition());
        try {
            if(db.insert(TABLE_NAME,null,values)== -1){
                return -1;
            }
        }catch (Exception ex){
            Log.e(TAG,ex.toString());
        }
        return 1;
    }
    //update
    public int updateBookType(BookType bookType){
        ContentValues values = new ContentValues();
        values.put(COLUMN_TYPE_ID,bookType.getTypeID());
        values.put(COLUMN_TYPE_NAME,bookType.getTypeName());
        values.put(COLUMN_DESCRIPTION,bookType.getDescription());
        values.put(COLUMN_POSITION,bookType.getPosition());
        int result = db.update(TABLE_NAME,values,"typeID=?", new
                String[]{bookType.getTypeID()});
        if (result == 0){
            return -1;
        }
        return 1;
    }
    //delete
    public int deleteBookTypeByID(String typecode){
        int result = db.delete(TABLE_NAME,"typeID=?",new String[]{typecode});
        if (result == 0)
            return -1;
        return 1;
    }
    // lấy ra tất cả thể loại
    public List<BookType> getAllBookType(){
        List<BookType> bookTypeList = new ArrayList<>();
        Cursor c = db.query(TABLE_NAME,null,null,null,null,null,null);
        c.moveToFirst();
        while (c.isAfterLast()==false){
            BookType bookType = new BookType();
            bookType.setTypeID(c.getString(0));
            bookType.setTypeName(c.getString(1));
            bookType.setDescription(c.getString(2));
            bookType.setPosition(c.getString(3));
            bookTypeList.add(bookType);
            c.moveToNext();
        }
        c.close();
        return bookTypeList;
    }
    public BookType getBookTypeByID(String typeID){
        BookType bookType = null;

        // Truyen vao Ten bang, array bao gom ten cot, ten cot khoa chinh, gia tri khoa chinh, cac tham so con lai la null

        Cursor cursor = db.query(TABLE_NAME, new String[]{
                        COLUMN_TYPE_ID,
                        COLUMN_TYPE_NAME,
                        COLUMN_DESCRIPTION,
                        COLUMN_POSITION},
                "typeID=?", new String[]{typeID},
                null,
                null,
                null,
                null);

        // moveToFirst : kiem tra xem cursor co chua du lieu khong, ham nay tra ve gia tri la true or false
        if (cursor != null && cursor.moveToFirst()) {

            String type_ID = cursor.getString(cursor.getColumnIndex(COLUMN_TYPE_ID));

            String typename = cursor.getString(cursor.getColumnIndex(COLUMN_TYPE_NAME));

            String description = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION));

            String position =cursor.getString(cursor.getColumnIndex(COLUMN_POSITION));

            // khoi tao user voi cac gia tri lay duoc
            bookType = new BookType(type_ID, typename, description, position);


        }
        cursor.close();

        return bookType;
    }
}