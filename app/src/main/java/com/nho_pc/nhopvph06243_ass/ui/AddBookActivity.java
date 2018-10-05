package com.nho_pc.nhopvph06243_ass.ui;

import android.content.Intent;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.nho_pc.nhopvph06243_ass.R;
import com.nho_pc.nhopvph06243_ass.dao.BookDAO;
import com.nho_pc.nhopvph06243_ass.dao.BookTypeDAO;
import com.nho_pc.nhopvph06243_ass.database.DatabaseHelper;
import com.nho_pc.nhopvph06243_ass.model.Book;

public class AddBookActivity extends AppCompatActivity {
    private Toolbar customtoolbarAddBook;
    private EditText edtBookCode;
    private Spinner spnBookType;
    private EditText edtBookName;
    private EditText edtAuthorName;
    private EditText edtPublisher;
    private EditText edtPriceBook;
    private EditText edtQuantily;
    private DatabaseHelper databaseHelper;
    private BookDAO bookDAO;
    private Cursor cursorType;
    private BookTypeDAO bookTypeDAO;
    private SimpleCursorAdapter adapterType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        setTitle("Thêm sách");
        databaseHelper = new DatabaseHelper(this);
        bookDAO = new BookDAO(this);
        bookTypeDAO=new BookTypeDAO(this);
        initViews();
        setSupportActionBar(customtoolbarAddBook);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        customtoolbarAddBook.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        cursorType = (Cursor) bookTypeDAO.getAllBookType();
        if (cursorType != null) {
            adapterType = new SimpleCursorAdapter(this,
                    android.R.layout.simple_spinner_item,
                    cursorType,
                    new String[]{"typename"},
                    new int[]{android.R.id.text1});
            spnBookType.setAdapter((SpinnerAdapter) cursorType);
        }
        spnBookType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Cursor c = (Cursor) spnBookType.getItemAtPosition(position);
                String typename = c.getString(2);
                Toast.makeText(getApplicationContext(), typename, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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

    public void addBook(View view) {
        String bookID = edtBookCode.getText().toString();
        String typeID = spnBookType.getSelectedItem().toString();
        String bookName = edtBookName.getText().toString();
        String authorname = edtAuthorName.getText().toString();
        String publisher = edtPublisher.getText().toString();
        String priceBook = edtPriceBook.getText().toString();
        String quantily = edtQuantily.getText().toString();
        if (bookID.isEmpty() || typeID.isEmpty() || bookName.isEmpty() || authorname.isEmpty()|| publisher.isEmpty()|| priceBook.isEmpty()|| quantily.isEmpty()) {
            if (bookID.isEmpty())
                edtBookCode.setError(getString(R.string.notify_empty_user_name));
            if (bookName.isEmpty())
                edtBookName.setError(getString(R.string.notify_empty_user_name));
            if (authorname.isEmpty())
                edtAuthorName.setError(getString(R.string.notify_empty_user_name));
            if (publisher.isEmpty())
                edtPublisher.setError(getString(R.string.notify_empty_user_name));
            if (priceBook.isEmpty())
                edtPriceBook.setError(getString(R.string.notify_empty_user_name));
            if (quantily.isEmpty())
                edtQuantily.setError(getString(R.string.notify_empty_user_name));
        } else {
            try {
                int priceBook1=Integer.parseInt(priceBook);
                Book book = new Book(bookID, typeID, bookName, authorname, publisher, priceBook1, quantily);
                bookDAO.inserBook(book);
                finish();
            }catch (Exception e){

            }
        }
    }

    public void cancelAddBook(View view) {
        finish();
        Toast.makeText(this, "Đã hủy thêm sách", Toast.LENGTH_SHORT).show();
    }

    public void addBookTypes(View view) {
        startActivity(new Intent(getApplicationContext(), AddBookTypeActivity.class));
    }
}
