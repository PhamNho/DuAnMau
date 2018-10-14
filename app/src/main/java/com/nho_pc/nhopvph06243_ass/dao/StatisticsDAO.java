package com.nho_pc.nhopvph06243_ass.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nho_pc.nhopvph06243_ass.database.DatabaseHelper;
import com.nho_pc.nhopvph06243_ass.listener.Constant;


public class StatisticsDAO implements Constant {

    private final DatabaseHelper databaseHelper;

    public StatisticsDAO(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public double getStatisticByDay() {
        double statistic = 0;
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        String sSQL = "SELECT SUM(statistic) from (SELECT SUM(Books.Price * BillDetail.Quantity) as statistic " +
                "FROM Bill INNER JOIN BillDetail on Bill.BillID = BillDetail.BillID " +
                "INNER JOIN Books on BillDetail.BookID = Books.BookID WHERE strftime('%Y-%m-%d', " + TB_COLUMN_BILL_DATE + "/ 1000, 'unixepoch') = strftime('%Y-%m-%d','now') GROUP BY BillDetail.BookID)tmp";
        Cursor c = db.rawQuery(sSQL, null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            statistic = c.getDouble(0);
            c.moveToNext();
        }
        c.close();
        return statistic;
    }
    public double getStatisticByMonth() {
        double statistic = 0;
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        String sSQL = "SELECT SUM(statistic) from (SELECT SUM(Books.Price * BillDetail.Quantity) as statistic " +
                "FROM Bill INNER JOIN BillDetail on Bill.BillID = BillDetail.BillID " +
                "INNER JOIN Books on BillDetail.BookID = Books.BookID WHERE strftime('%Y-%m', " + TB_COLUMN_BILL_DATE + "/ 1000, 'unixepoch') = strftime('%Y-%m','now') GROUP BY BillDetail.BookID)tmp";
        Cursor c = db.rawQuery(sSQL, null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            statistic = c.getDouble(0);
            c.moveToNext();
        }
        c.close();
        return statistic;
    }

    public double getStatisticByYear() {
        double statistic = 0;
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        String sSQL = "SELECT SUM(statistic) from (SELECT SUM(Books.Price * BillDetail.Quantity) as statistic " +
                "FROM Bill INNER JOIN BillDetail on Bill.BillID = BillDetail.BillID " +
                "INNER JOIN Books on BillDetail.BookID = Books.BookID WHERE strftime('%Y', " + TB_COLUMN_BILL_DATE + "/ 1000, 'unixepoch') = strftime('%Y','now') GROUP BY BillDetail.BookID)tmp";
        Cursor c = db.rawQuery(sSQL, null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            statistic = c.getDouble(0);
            c.moveToNext();
        }
        c.close();
        return statistic;
    }
}
