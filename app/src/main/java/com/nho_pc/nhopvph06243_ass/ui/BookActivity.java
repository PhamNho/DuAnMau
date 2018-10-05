package com.nho_pc.nhopvph06243_ass.ui;

import android.content.Intent;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.nho_pc.nhopvph06243_ass.R;
import com.nho_pc.nhopvph06243_ass.adapter.BookAdapter;
import com.nho_pc.nhopvph06243_ass.dao.BookDAO;
import com.nho_pc.nhopvph06243_ass.database.DatabaseHelper;
import com.nho_pc.nhopvph06243_ass.model.Book;

import java.util.ArrayList;
import java.util.List;

public class BookActivity extends AppCompatActivity {
    private Toolbar customtoolbarBook;
    private ListView lvBook;
    private List<Book> bookList=new ArrayList<>();
    private BookDAO bookDAO;
    private BookAdapter bookAdapter=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        setTitle("Sách");
        customtoolbarBook = (Toolbar) findViewById(R.id.customtoolbarBook);
        setSupportActionBar(customtoolbarBook);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        customtoolbarBook.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                finish();
            }
        });
        lvBook = findViewById(R.id.lvBook);
        bookDAO=new BookDAO(BookActivity.this);
        bookList=bookDAO.getAllBook();
        bookAdapter=new BookAdapter(this,bookList);
        lvBook.setAdapter(bookAdapter);
    }
    @Override
    protected void onResume() {
        super.onResume();
        bookList.clear();
        bookList = bookDAO.getAllBook();
        bookAdapter.changeDataset(bookDAO.getAllBook());
    }

    public void addBooks(View view) {
        startActivity(new Intent(getApplicationContext(),AddBookActivity.class));
        finish();
    }
}
