package com.nho_pc.nhopvph06243_ass.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.nho_pc.nhopvph06243_ass.Constant;

import static com.nho_pc.nhopvph06243_ass.dao.UserDAO.COLUMN_NAME;
import static com.nho_pc.nhopvph06243_ass.dao.UserDAO.COLUMN_PASSWORD;
import static com.nho_pc.nhopvph06243_ass.dao.UserDAO.COLUMN_PHONE_NUMBER;
import static com.nho_pc.nhopvph06243_ass.dao.UserDAO.COLUMN_USERNAME;
import static com.nho_pc.nhopvph06243_ass.dao.UserDAO.USER_TABLE;

public class DatabaseHelper extends SQLiteOpenHelper {

    public final static String CREATE_USER_TABLE = "CREATE TABLE " + USER_TABLE + "(" +
            COLUMN_USERNAME + " VARCHAR PRIMARY KEY," +
            COLUMN_PASSWORD + " VARCHAR," +
            COLUMN_NAME + " VARCHAR," +
            COLUMN_PHONE_NUMBER + " VARCHAR" +
            ")";

    public DatabaseHelper(Context context) {
        super(context, "BookManager", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // create User Table
        db.execSQL(CREATE_USER_TABLE);
        if (Constant.isDEBUG) Log.e("CREATE_USER_TABLE", CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
        onCreate(db);
    }
}
