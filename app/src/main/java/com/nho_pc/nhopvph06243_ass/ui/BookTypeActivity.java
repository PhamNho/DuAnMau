package com.nho_pc.nhopvph06243_ass.ui;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.nho_pc.nhopvph06243_ass.R;
import com.nho_pc.nhopvph06243_ass.adapter.BookTypeAdapter;
import com.nho_pc.nhopvph06243_ass.dao.BookTypeDAO;
import com.nho_pc.nhopvph06243_ass.database.DatabaseHelper;
import com.nho_pc.nhopvph06243_ass.model.BookType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BookTypeActivity extends AppCompatActivity {
    private Toolbar customtoolbarBookType;
    private ListView lvBookType;
    private static List<BookType> bookTypeList=new ArrayList<>();
    private DatabaseHelper databaseHelper;
    private BookTypeDAO bookTypeDAO;
    private BookTypeAdapter bookTypeAdapter=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_type);
        setTitle("Thể loại");
        initViews();
        customtoolbarBookType = findViewById(R.id.customtoolbarBookType);
        setSupportActionBar(customtoolbarBookType);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        customtoolbarBookType.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        lvBookType = findViewById(R.id.lvBookType);
        bookTypeDAO=new BookTypeDAO(BookTypeActivity.this);
        bookTypeList=bookTypeDAO.getAllBookType();

        bookTypeAdapter = new BookTypeAdapter(this, bookTypeList);
        lvBookType.setAdapter(bookTypeAdapter);
    }
    @Override
    protected void onResume() {
        super.onResume();
        bookTypeList.clear();
        bookTypeList = bookTypeDAO.getAllBookType();
        bookTypeAdapter.changeDataset(bookTypeDAO.getAllBookType());
    }

    private void initViews() {
        databaseHelper=new DatabaseHelper(this);
        bookTypeDAO=new BookTypeDAO(this);
        lvBookType=findViewById(R.id.lvBookType);
    }

    public void addBookTypes(View view) {
        startActivity(new Intent(getApplicationContext(),AddBookTypeActivity.class));
    }
}
