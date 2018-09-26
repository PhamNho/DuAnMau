package com.nho_pc.nhopvph06243_ass.listener;

public interface Constant {
    boolean isDEBUG=true;

    // TABLE USER
    String USER_TABLE = "users";
    String COLUMN_USERNAME = "username";
    String COLUMN_PASSWORD = "password";
    String COLUMN_NAME = "name";
    String COLUMN_PHONE_NUMBER = "phonenumber";

    String CREATE_USER_TABLE = "CREATE TABLE " + USER_TABLE + "(" +
            COLUMN_USERNAME + " VARCHAR PRIMARY KEY," +
            COLUMN_PASSWORD + " VARCHAR," +
            COLUMN_NAME + " VARCHAR," +
            COLUMN_PHONE_NUMBER + " VARCHAR" +
            ")";

    //===============

    // TABLE BOOK
}
