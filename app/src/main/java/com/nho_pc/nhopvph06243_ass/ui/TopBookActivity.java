package com.nho_pc.nhopvph06243_ass.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.nho_pc.nhopvph06243_ass.R;

public class TopBookActivity extends AppCompatActivity {
    private Toolbar customtoolbarTopBook;
    private ListView lvTopBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_book);
        setTitle("Top 10 sách bán chạy");
        initViews();
        setSupportActionBar(customtoolbarTopBook);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //
        customtoolbarTopBook.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                finish();
            }
        });
    }

    private void initViews() {
        customtoolbarTopBook = (Toolbar) findViewById(R.id.customtoolbarTopBook);
        lvTopBook = (ListView) findViewById(R.id.lvTopBook);
    }

    public void shareBook(View view) {

    }
}
