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
import java.util.Objects;

public class BookTypeDAO implements Constant{

    private final DatabaseHelper databaseHelper;
    public BookTypeDAO(Context context) {
        this.databaseHelper = new DatabaseHelper(context);
    }

    public long insertTypeBook(BookType typeBook) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TB_COLUMN_TYPE_BOOK_ID, typeBook.getId());
        contentValues.put(TB_COLUMN_DESCRIPTION, typeBook.getDescription());
        contentValues.put(TB_COLUMN_TYPE_NAME, typeBook.getName());
        contentValues.put(TB_COLUMN_POSITION, typeBook.getPosition());
        long result = db.insert(TYPE_BOOK_TABLE, null, contentValues);
        if (Constant.isDEBUG) Log.e("insertTypeBook", "insertTypeBook ID : " + typeBook.getId());
        db.close();
        return result;
    }
    public long deleteTypeBook(String typeId) {

        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        long result = db.delete(TYPE_BOOK_TABLE, TB_COLUMN_TYPE_BOOK_ID + " = ?",
                new String[]{String.valueOf(typeId)});
        db.close();
        return result;

    }
    public long updateTypeBook(BookType typeBook) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TB_COLUMN_DESCRIPTION, typeBook.getDescription());
        contentValues.put(TB_COLUMN_TYPE_NAME, typeBook.getName());
        contentValues.put(TB_COLUMN_POSITION, typeBook.getPosition());
        return db.update(TYPE_BOOK_TABLE, contentValues, TB_COLUMN_TYPE_BOOK_ID + " = ?",
                new String[]{String.valueOf(typeBook.getId())});
    }
    public List<BookType> getAllTypeBooks() {

        List<BookType> typeBooks = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TYPE_BOOK_TABLE;

        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                String id = cursor.getString(cursor.getColumnIndex(TB_COLUMN_TYPE_BOOK_ID));
                String name = cursor.getString(cursor.getColumnIndex(TB_COLUMN_TYPE_NAME));
                String description = cursor.getString(cursor.getColumnIndex(TB_COLUMN_DESCRIPTION));
                String position = cursor.getString(cursor.getColumnIndex(TB_COLUMN_POSITION));
                BookType typeBook = new BookType();
                typeBook.setId(id);
                typeBook.setName(name);
                typeBook.setDescription(description);
                typeBook.setPosition(position);
                typeBooks.add(typeBook);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return typeBooks;

    }

    //    public List<BookType> getAllTypeBooksLike(String ID) {
//
//        List<BookType> typeBooks = new ArrayList<>();
//        String selectQuery = "SELECT  * FROM " + TYPE_BOOK_TABLE+ " WHERE " + TB_COLUMN_TYPE_BOOK_ID + " like '%" + ID + "%'";
//
//        SQLiteDatabase db = databaseHelper.getWritableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, null);
//        if (cursor.moveToFirst()) {
//            do {
//                String id = cursor.getString(cursor.getColumnIndex(TB_COLUMN_TYPE_BOOK_ID));
//                String name = cursor.getString(cursor.getColumnIndex(TB_COLUMN_TYPE_NAME));
//                String description = cursor.getString(cursor.getColumnIndex(TB_COLUMN_DESCRIPTION));
//                String position = cursor.getString(cursor.getColumnIndex(TB_COLUMN_POSITION));
//                BookType typeBook = new BookType();
//                typeBook.setId(id);
//                typeBook.setName(name);
//                typeBook.setDescription(description);
//                typeBook.setPosition(position);
//                typeBooks.add(typeBook);
//            } while (cursor.moveToNext());
//        }
//        cursor.close();
//        db.close();
//        return typeBooks;
//
//    }
    public BookType getTypeBookByID(String typeID) {

        BookType typeBook = null;

        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        Cursor cursor = db.query(TYPE_BOOK_TABLE, new String[]{TB_COLUMN_TYPE_BOOK_ID, TB_COLUMN_TYPE_NAME, TB_COLUMN_DESCRIPTION, TB_COLUMN_POSITION},
                TB_COLUMN_TYPE_BOOK_ID + "=?", new String[]{typeID},
                null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {

            String id = cursor.getString(cursor.getColumnIndex(TB_COLUMN_TYPE_BOOK_ID));

            String name = cursor.getString(cursor.getColumnIndex(TB_COLUMN_TYPE_NAME));

            String description = cursor.getString(cursor.getColumnIndex(TB_COLUMN_DESCRIPTION));

            String position = cursor.getString(cursor.getColumnIndex(TB_COLUMN_POSITION));

            typeBook = new BookType(id,name,description,position);
        }
        Objects.requireNonNull(cursor).close();
        return typeBook;

    }
    public Cursor getTypeBook() {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        return db.rawQuery("Select * from "+TYPE_BOOK_TABLE, null);
    }

}
