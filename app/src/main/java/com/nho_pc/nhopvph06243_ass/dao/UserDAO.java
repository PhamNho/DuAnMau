package com.nho_pc.nhopvph06243_ass.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nho_pc.nhopvph06243_ass.database.DatabaseHelper;
import com.nho_pc.nhopvph06243_ass.listener.Constant;
import com.nho_pc.nhopvph06243_ass.model.Users;

import java.util.Objects;

public class UserDAO implements Constant {
    private final SQLiteDatabase database;

    public UserDAO(Context context) {
        DatabaseHelper databaseHelper =new DatabaseHelper(context);
        database=databaseHelper.getWritableDatabase();
    }
    public int insertUser(String user, String pass, String name, String sdt) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_USERNAME,user);
        values.put(DatabaseHelper.COLUMN_PASSWORD,pass);
        values.put(DatabaseHelper.COLUMN_NAME,name);
        values.put(DatabaseHelper.COLUMN_PHONE_NUMBER,sdt);
        try {
            if( database.insert(DatabaseHelper.USER_TABLE,null,values)==-1){
                return -1;
            }
        }catch (Exception ignored){
        }
        return 1;
    }
    public int updateUser(String user,String name,String sdt) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_NAME,name);
        values.put(DatabaseHelper.COLUMN_PHONE_NUMBER,sdt);
        int result=database.update(DatabaseHelper.USER_TABLE, values, DatabaseHelper.COLUMN_USERNAME +" = ?", new String[]{user});
        if (result == 0){
            return -1;
        }
        return 1;
    }
    public int updatePassWord(String user,String password) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_PASSWORD,password);
        int result = database.update(DatabaseHelper.USER_TABLE, values, DatabaseHelper.COLUMN_USERNAME +" = ?", new String[]{user});
        if (result == 0){
            return -1;
        }
        return 1;
    }
    public Cursor getUser() {
        return database.rawQuery("Select * from "+DatabaseHelper.USER_TABLE, null);
    }
    public int deleteUser(String user) {
        int result=database.delete(DatabaseHelper.USER_TABLE, DatabaseHelper.COLUMN_USERNAME + " = ?",new String[]{user});
        if (result == 0){
            return -1;
        }
        return 1;
    }
    public Users getUser(String username) {
        Users users = null;

        // Truyen vao Ten bang, array bao gom ten cot, ten cot khoa chinh, gia tri khoa chinh, cac tham so con lai la null

        Cursor cursor = database.query(USER_TABLE, new String[]{
                        COLUMN_USERNAME,
                        COLUMN_PASSWORD,
                        COLUMN_NAME,
                        COLUMN_PHONE_NUMBER},
                COLUMN_USERNAME+"=? ", new String[]{username},
                null,
                null,
                null,
                null);

        // moveToFirst : kiem tra xem cursor co chua du lieu khong, ham nay tra ve gia tri la true or false
        if (cursor != null && cursor.moveToFirst()) {

            String user_name = cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME));

            String password = cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD));

            String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));

            String phoneNumber = cursor.getString(cursor.getColumnIndex(COLUMN_PHONE_NUMBER));

            // khoi tao user voi cac gia tri lay duoc
            users = new Users(user_name, password, name, phoneNumber);


        }
        Objects.requireNonNull(cursor).close();

        return users;
    }
}
