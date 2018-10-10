package com.nho_pc.nhopvph06243_ass.ui;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.nho_pc.nhopvph06243_ass.R;
import com.nho_pc.nhopvph06243_ass.dao.BookTypeDAO;
import com.nho_pc.nhopvph06243_ass.database.DatabaseHelper;
import com.nho_pc.nhopvph06243_ass.model.BookType;

public class AddBookTypeActivity extends AppCompatActivity {
    private Toolbar customtoolbarAddBook;
    private EditText edtBookTypeID;
    private EditText edtBookTypeName;
    private EditText edtPositionType;
    private EditText edtDescribe;
    private DatabaseHelper databaseHelper;
    private BookTypeDAO bookTypeDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book_type);
        setTitle("Thêm thể loại");
        databaseHelper = new DatabaseHelper(this);
        bookTypeDAO = new BookTypeDAO(this);
        initViews();
        setSupportActionBar(customtoolbarAddBook);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        customtoolbarAddBook.setTitleTextColor(Color.BLACK);
        customtoolbarAddBook.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void initViews() {
        customtoolbarAddBook = (Toolbar) findViewById(R.id.customtoolbarAddBook);
        edtBookTypeID = (EditText) findViewById(R.id.edtBookTypeID);
        edtBookTypeName = (EditText) findViewById(R.id.edtBookTypeName);
        edtPositionType = (EditText) findViewById(R.id.edtPositionType);
        edtDescribe = (EditText) findViewById(R.id.edtDescribe);
    }

    public void addBookType(View view) {
        String typeID = edtBookTypeID.getText().toString().trim();
        String typeName = edtBookTypeName.getText().toString();
        String position = edtPositionType.getText().toString();
        String describe = edtDescribe.getText().toString();

        if (typeID.isEmpty() || typeName.isEmpty() || position.isEmpty() || describe.isEmpty()) {
            if (typeID.isEmpty())
                edtBookTypeID.setError(getString(R.string.notify_empty_user_name));
            if (typeName.isEmpty())
                edtBookTypeName.setError(getString(R.string.notify_empty_user_name));
            if (position.isEmpty())
                edtPositionType.setError(getString(R.string.notify_empty_user_name));
            if (describe.isEmpty())
                edtDescribe.setError(getString(R.string.notify_empty_user_name));
        } else {
            BookType bookType = new BookType(typeID, typeName, describe, position);
            bookTypeDAO.inserBookType(bookType);
            finish();
            Toast.makeText(this, "Đã thêm", Toast.LENGTH_SHORT).show();
        }
    }

    public void huyaddBookType(View view) {
        finish();
    }
}
