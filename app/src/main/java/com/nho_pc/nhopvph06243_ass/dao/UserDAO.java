package com.nho_pc.nhopvph06243_ass.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.nho_pc.nhopvph06243_ass.listener.Constant;
import com.nho_pc.nhopvph06243_ass.database.DatabaseHelper;
import com.nho_pc.nhopvph06243_ass.model.Users;

import java.util.ArrayList;
import java.util.List;

public class UserDAO implements  Constant{
    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;
    public static final String TABLE_NAME = "users";

    public UserDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    //insert
    public long insertUser(Users user) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USERNAME, user.getUserName());
        contentValues.put(COLUMN_PASSWORD, user.getPassword());
        contentValues.put(COLUMN_NAME, user.getName());
        contentValues.put(COLUMN_PHONE_NUMBER, user.getPhoneNumber());
        long result = db.insert(TABLE_NAME, null,contentValues);
        if (result == 0) {
            return -1;
        }
        return 1;
    }
    // getUser
    public Users getUserByID(String username, String password1) {

        Users users = null;

        // Truyen vao Ten bang, array bao gom ten cot, ten cot khoa chinh, gia tri khoa chinh, cac tham so con lai la null

        Cursor cursor = db.query(TABLE_NAME, new String[]{
                COLUMN_USERNAME,
                COLUMN_PASSWORD,
                COLUMN_NAME,
                COLUMN_PHONE_NUMBER},
                "username=? AND password=?", new String[]{username,password1},
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
        cursor.close();

        return users;
    }

    //getAll
    public List<Users> getAllUsers() {
        List<Users> usersList = new ArrayList<>();
        Cursor c = db.query(TABLE_NAME, null, null, null, null, null, null);
        c.moveToFirst();
        while (c.isAfterLast() == false) {
            Users users=new Users();
            users.setUserName(c.getString(0));
            users.setPassword(c.getString(1));
            users.setName(c.getString(2));
            users.setPhoneNumber(c.getString(3));
            usersList.add(users);
            c.moveToNext();
        }
        c.close();
        return usersList;
    }

    //update
    public int updateUsers(Users users) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, users.getUserName());
        values.put(COLUMN_PASSWORD, users.getPassword());
        values.put(COLUMN_NAME, users.getName());
        values.put(COLUMN_PHONE_NUMBER, users.getPhoneNumber());
        int result = db.update(TABLE_NAME, values, "username=?", new String[]{users.getUserName()});
        if (result == 0) {
            return -1;
        }
        return 1;
    }

    public int changePasswordUsers(Users users){
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME,users.getUserName());
        values.put(COLUMN_PASSWORD,users.getPassword());
        int result = db.update(TABLE_NAME,values,"username=?", new String[]{users.getUserName()});
        if (result == 0){
            return -1;
        }
        return 1;
    }

    public int updateInfoUsers(String username, String name, String phone) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_PHONE_NUMBER, phone);
        int result = db.update(TABLE_NAME, values, "username=?", new String[]{username});
        if (result == 0) {
            return -1;
        }
        return 1;
    }
    //delete
    public int deleteUsersByID(String username){
        int result = db.delete(TABLE_NAME,"username=?",new String[]{username});
        if (result == 0)
            return -1;
        return 1;
    }
    //check login
    public int checkLogin(String username, String password){
        int result = db.delete(TABLE_NAME,"username=? AND password=?",new String[]{username,password});
        if (result == 0)
            return -1;
        return 1;
    }
}
