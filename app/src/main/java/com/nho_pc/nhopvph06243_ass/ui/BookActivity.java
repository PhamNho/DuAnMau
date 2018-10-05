package com.nho_pc.nhopvph06243_ass.ui;

import android.content.Intent;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
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
        lvBook = (ListView) findViewById(R.id.lvBook);
        bookDAO = new BookDAO(BookActivity.this);
        bookList = bookDAO.getAllBook();
        bookAdapter = new BookAdapter(this, bookList);
        lvBook.setAdapter(bookAdapter);
        lvBook.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Book book = (Book) parent.getItemAtPosition(position);
                Intent intent = new Intent(BookActivity.this,AddBookActivity.class);
                Bundle b = new Bundle();
                b.putString("MASACH", book.getMaSach());
                b.putString("MATHELOAI", book.getMaTheLoai());
                b.putString("TENSACH", book.getTenSach());
                b.putString("TACGIA", book.getTacGia());
                b.putString("NXB", book.getNXB());
                b.putString("GIABIA", String.valueOf(book.getGiaBia()));
                b.putString("SOLUONG", String.valueOf(book.getSoLuong()));
                intent.putExtras(b);
                startActivity(intent);
            }
        });
        // TextFilter
        lvBook.setTextFilterEnabled(true);
        EditText edSeach = (EditText) findViewById(R.id.edSearchBook);
        edSeach.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                System.out.println("Text [" + s + "] - Start [" + start + "] - Before [" + before + "] - Count [" + count + "]");
                if (count < before) {
                    bookAdapter.resetData();
                }
                bookAdapter.getFilter().filter(s.toString());
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    public void addBooks(View view) {
        startActivity(new Intent(getApplicationContext(),AddBookActivity.class));
        finish();
    }
}
