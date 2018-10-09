package com.nho_pc.nhopvph06243_ass.ui;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.nho_pc.nhopvph06243_ass.R;
import com.nho_pc.nhopvph06243_ass.adapter.BookAdapter;
import com.nho_pc.nhopvph06243_ass.adapter.TypeBookSpinnerAdapter;
import com.nho_pc.nhopvph06243_ass.dao.BookDAO;
import com.nho_pc.nhopvph06243_ass.dao.BookTypeDAO;
import com.nho_pc.nhopvph06243_ass.database.DatabaseHelper;
import com.nho_pc.nhopvph06243_ass.model.Book;
import com.nho_pc.nhopvph06243_ass.model.BookType;

import java.util.ArrayList;
import java.util.List;

public class AddBookActivity extends AppCompatActivity {
    private Toolbar customtoolbarAddBook;
    private EditText edtBookCode;
    private Spinner spnBookType;
    private EditText edtBookName;
    private EditText edtAuthorName;
    private EditText edtPublisher;
    private EditText edtPriceBook;
    private EditText edtQuantily;
    private BookDAO bookDAO;
    private BookTypeDAO bookTypeDAO;
    private String maTheLoai = "";
    private List<BookType> listTheLoai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        setTitle("Thêm sách");
        bookDAO = new BookDAO(this);
        bookTypeDAO = new BookTypeDAO(this);
        initViews();
        getType();
        customtoolbarAddBook = (Toolbar) findViewById(R.id.customtoolbarAddBook);
        setSupportActionBar(customtoolbarAddBook);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        customtoolbarAddBook.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        listTheLoai = new BookTypeDAO(this).getAllBookType();
        spnBookType.setAdapter(new TypeBookSpinnerAdapter(this, listTheLoai));
        //load data into form
        Intent in = getIntent();
        Bundle b = in.getExtras();
        if (b != null) {
            edtBookCode.setText(b.getString("MASACH"));
            String maTheLoai = b.getString("MATHELOAI");
            edtBookName.setText(b.getString("TENSACH"));
            edtPublisher.setText(b.getString("NXB"));
            edtAuthorName.setText(b.getString("TACGIA"));
            edtPriceBook.setText(b.getString("GIABIA"));
            edtQuantily.setText(b.getString("SOLUONG"));
            spnBookType.setSelection(checkPositionTheLoai(maTheLoai));
        }
    }
    public void getType(){
        bookTypeDAO = new BookTypeDAO(AddBookActivity.this);
        listTheLoai = bookTypeDAO.getAllBookType();
        ArrayAdapter<BookType> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listTheLoai);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnBookType.setAdapter(dataAdapter);
    }

    public int checkPositionTheLoai(String strTheLoai){
        for (int i = 0; i <listTheLoai.size(); i++){
            if (strTheLoai.equals(listTheLoai.get(i).getTypeID())){
                return i;
            }
        }
        return 0;
    }

    private void initViews() {

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
        if (bookID.isEmpty() || typeID.isEmpty() || bookName.isEmpty() || authorname.isEmpty() || publisher.isEmpty() || priceBook.isEmpty() || quantily.isEmpty()) {
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
                Double priceBook1 =Double.parseDouble(priceBook);
                int quantily1=Integer.parseInt(quantily);
                Book book = new Book(bookID, typeID, bookName, authorname, publisher, priceBook1, quantily1);
                bookDAO.inserBook(book);
                Toast.makeText(this, "Đã thêm", Toast.LENGTH_SHORT).show();
                finish();
            } catch (Exception e) {

            }
        }
    }

    public void cancelAddBook(View view) {
        finish();
        Toast.makeText(this, "Đã hủy thêm sách", Toast.LENGTH_SHORT).show();
    }

    public void addBookTypes(View view) {
        startActivity(new Intent(getApplicationContext(), AddBookTypeActivity.class));
        finish();
    }
}
