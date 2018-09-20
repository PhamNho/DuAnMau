package com.nho_pc.nhopvph06243_ass.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.nho_pc.nhopvph06243_ass.R;

public class AddBookActivity extends AppCompatActivity {
    private Toolbar customtoolbarAddBook;
    private EditText edtBookCode;
    private Spinner spnBookType;
    private EditText edtBookName;
    private EditText edtAuthorName;
    private EditText edtPublisher;
    private EditText edtPriceBook;
    private EditText edtQuantily;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        setTitle("Thêm sách");
        initViews();
        setSupportActionBar(customtoolbarAddBook);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        customtoolbarAddBook.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),BookActivity.class));
                finish();
            }
        });

    }

    private void initViews() {
        customtoolbarAddBook = (Toolbar) findViewById(R.id.customtoolbarAddBook);
        edtBookCode = (EditText) findViewById(R.id.edtBookCode);
        spnBookType = (Spinner) findViewById(R.id.spnBookType);
        edtBookName = (EditText) findViewById(R.id.edtBookName);
        edtAuthorName = (EditText) findViewById(R.id.edtAuthorName);
        edtPublisher = (EditText) findViewById(R.id.edtPublisher);
        edtPriceBook = (EditText) findViewById(R.id.edtPriceBook);
        edtQuantily = (EditText) findViewById(R.id.edtQuantily);
    }

    public void addBookType(View view) {

    }

    public void addBook(View view) {

    }

    public void cancelAddBook(View view) {
        finish();
        Toast.makeText(this, "Đã hủy thêm sách", Toast.LENGTH_SHORT).show();
    }
}
