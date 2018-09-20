package com.nho_pc.nhopvph06243_ass.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.nho_pc.nhopvph06243_ass.R;

public class AddBookTypeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book_type);
        setTitle("Thêm thể loại");

    }

    public void addBookType(View view) {

    }

    public void huyaddBookType(View view) {
        finish();
    }
}
