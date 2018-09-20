package com.nho_pc.nhopvph06243_ass.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.nho_pc.nhopvph06243_ass.R;

public class BookTypeActivity extends AppCompatActivity {
    private Toolbar customtoolbarBookType;
    private ListView lvBookType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_type);
        setTitle("Thể loại");
        initViews();
        setSupportActionBar(customtoolbarBookType);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        customtoolbarBookType.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initViews() {
        customtoolbarBookType = (Toolbar) findViewById(R.id.customtoolbarBookType);
        lvBookType = (ListView) findViewById(R.id.lvBookType);
    }

    public void addBookTypes(View view) {
        startActivity(new Intent(getApplicationContext(),AddBookTypeActivity.class));
        finish();
    }
}
