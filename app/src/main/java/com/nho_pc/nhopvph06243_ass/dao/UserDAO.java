package com.nho_pc.nhopvph06243_ass.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.nho_pc.nhopvph06243_ass.database.DatabaseHelper;
import com.nho_pc.nhopvph06243_ass.model.Users;

import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;
    public static final String TABLE_NAME = "Users";
    public static final String SQL_USER = "CREATE TABLE Users(username text primary key, password text, name text, phonenumber text);";
    public static final String TAG = "UserDAO";

    public UserDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    //insert
    public int inserUsers(Users users) {
        ContentValues values = new ContentValues();
        values.put("username", users.getUserName());
        values.put("password", users.getPassword());
        values.put("name", users.getName());
        values.put("phonenumber", users.getPhoneNumber());
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
            Log.d("getAllUsers", users.toString());
            c.moveToNext();
        }
        c.close();
        return usersList;
    }

    //update
    public int updateUsers(Users users) {
        ContentValues values = new ContentValues();
        values.put("username", users.getUserName());
        values.put("password", users.getPassword());
        values.put("name", users.getName());
        values.put("phonenumber", users.getPhoneNumber());
        int result = db.update(TABLE_NAME, values, "username=?", new String[]{users.getUserName()});
        if (result == 0) {
            return -1;
        }
        return 1;
    }

    public int changePasswordUsers(Users users) {
        ContentValues values = new ContentValues();
        values.put("username", users.getUserName());
        values.put("password", users.getPassword());
        int result = db.update(TABLE_NAME, values, "username=?", new String[]{users.getUserName()});
        if (result == 0) {
            return -1;
        }
        return 1;
    }

    public int updateInfoUsers(String username, String name, String phone) {
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("phonenumber", phone);
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
