package com.nho_pc.nhopvph06243_ass.ui;

import android.content.Intent;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.nho_pc.nhopvph06243_ass.R;

public class BookActivity extends AppCompatActivity {
    private Toolbar customtoolbarBook;
    private ListView lvBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        setTitle("Sách");
        customtoolbarBook = (Toolbar) findViewById(R.id.customtoolbarBook);
        lvBook = (ListView) findViewById(R.id.lvBook);

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
    }

    public void addBooks(View view) {
        startActivity(new Intent(getApplicationContext(),AddBookActivity.class));
        finish();
    }
}
