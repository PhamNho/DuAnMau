package com.nho_pc.nhopvph06243_ass.listener;

public interface Constant {
    boolean isDEBUG = true;

    // TABLE USER
    String USER_TABLE = "users";
    String COLUMN_USERNAME = "username";
    String COLUMN_PASSWORD = "password";
    String COLUMN_NAME = "name";
    String COLUMN_PHONE_NUMBER = "phonenumber";

    String CREATE_USER_TABLE = "CREATE TABLE " + USER_TABLE + "(" +
            COLUMN_USERNAME + " VARCHAR PRIMARY KEY NOT NULL," +
            COLUMN_PASSWORD + " VARCHAR NOT NULL," +
            COLUMN_NAME + " VARCHAR NOT NULL," +
            COLUMN_PHONE_NUMBER + " VARCHAR NOT NULL" +
            ")";

    //===============

    // TABLE BOOK TYPE
    String TYPE_TABLE = "BookType";
    String COLUMN_TYPE_ID = "typeID";
    String COLUMN_TYPE_NAME = "typename";
    String COLUMN_DESCRIPTION = "description";
    String COLUMN_POSITION = "position";

    String CREATE_BOOK_TYPE_TABLE = "CREATE TABLE " + TYPE_TABLE + "(" +
            COLUMN_TYPE_ID + " CHAR(5) PRIMARY KEY," +
            COLUMN_TYPE_NAME + " NVARCHAR(50)," +
            COLUMN_DESCRIPTION + " NVARCHAR(255)," +
            COLUMN_POSITION + " INT" +
            ")";
    //===============

    // TABLE BOOK
    String BOOK_TABLE = "book";
    String COLUMN_BOOK_ID = "bookID";
    String COLUMN_BOOK_NAME = "bookname";
    String COLUMN_TAC_GIA = "tacgia";
    String COLUMN_NXB = "nxb";
    String COLUMN_GIA_BIA = "giabia";
    String COLUMN_SO_LUONG = "soluong";

    String CREATE_BOOK_TABLE = "CREATE TABLE " + BOOK_TABLE + "(" +
            COLUMN_BOOK_ID + " VARCHAR PRIMARY KEY NOT NULL," +
            COLUMN_TYPE_ID + " CHAR(5) NOT NULL," +
            COLUMN_BOOK_NAME + " VARCHAR NOT NULL," +
            COLUMN_TAC_GIA + " VARCHAR NOT NULL," +
            COLUMN_NXB + " VARCHAR NOT NULL," +
            COLUMN_GIA_BIA + " DOUBLE NOT NULL," +
            COLUMN_SO_LUONG + " INT NOT NULL" +
            ")";

    //===============

    // TABLE BILL
    String BILL_TABLE = "bill";
    String COLUMN_BILL_ID = "billID";
    String COLUMN_DATE_BILL = "datebill";

    String CREATE_BILL_TABLE = "CREATE TABLE " + BILL_TABLE + "(" +
            COLUMN_BILL_ID + " VARCHAR PRIMARY KEY NOT NULL," +
            COLUMN_DATE_BILL + " DATE NOT NULL," +
            COLUMN_BOOK_ID + " VARCHAR NOT NULL" +
            ")";

    //===============
//public static final String SQL_HOA_DON_CHI_TIET = "CREATE TABLE HoaDonChiTiet(maHDCT INTEGER PRIMARY KEY AUTOINCREMENT, " +
//        "maHoaDon text NOT NULL, maSach text NOT NULL, soLuong INTEGER);";
    // TABLE BILLDETAIL
    String BILLDETAIL_TABLE = "billdetail";
    String COLUMN_BILLDETAIL_ID = "billdetailID";
    int COLUMN_SO_LUONG_MUA = 10;
    String CREATE_BILLDETAIL_TABLE = "CREATE TABLE " + BILLDETAIL_TABLE + "(" +
            COLUMN_BILLDETAIL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_BILL_ID + " VARCHAR NOT NULL," +
            COLUMN_BOOK_ID + " VARCHAR NOT NULL," +
            COLUMN_SO_LUONG_MUA + " INT NOT NULL" +
            ")";
}