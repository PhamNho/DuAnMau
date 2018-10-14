package com.nho_pc.nhopvph06243_ass.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;

import com.nho_pc.nhopvph06243_ass.R;
import com.nho_pc.nhopvph06243_ass.adapter.SpinerMonthAdapter;
import com.nho_pc.nhopvph06243_ass.adapter.Top10BookAdapter;
import com.nho_pc.nhopvph06243_ass.dao.BookDAO;
import com.nho_pc.nhopvph06243_ass.model.Book;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Top10BookActivity extends AppCompatActivity {
    private Toolbar customtoolbarTop10Book;
    private RecyclerView lvListBestBookSelling;
    private List<Book> bookList;
    private Top10BookAdapter top10BookAdapter;
    private BookDAO bookDAO;
    private Spinner spMonth;
    @SuppressLint("SimpleDateFormat")
    private final SimpleDateFormat dateFormat =new SimpleDateFormat();
    @SuppressLint("SimpleDateFormat")
    private final SimpleDateFormat dateFormat2 =new SimpleDateFormat();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_book);
        setTitle(getString(R.string.title_top10_book));
        initViews();

        final String month[] = new String[]{"Tháng 1","Tháng 2","Tháng 3","Tháng 4","Tháng 5","Tháng 6","Tháng 7",
                "Tháng 8","Tháng 9","Tháng 10","Tháng 11","Tháng 12"};

        dateFormat.applyPattern("MM");
        dateFormat2.applyPattern("yyyy");
        String monthnow = dateFormat.format(new Date());
        final String yearnow = dateFormat2.format(new Date(System.currentTimeMillis()));
        bookDAO = new BookDAO(this);
        SpinerMonthAdapter adapter = new SpinerMonthAdapter(this,month);
        spMonth.setAdapter(adapter);
        spMonth.setSelection(Integer.parseInt(monthnow)-1);
        spMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String month;
                if(position<9){
                    month = yearnow+"-0"+(position+1);
                }else{
                    month = yearnow+"-"+(position+1);

                }
                bookList = bookDAO.getSachTop10(month);
                top10BookAdapter = new Top10BookAdapter(bookList);
                lvListBestBookSelling.setAdapter(top10BookAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        lvListBestBookSelling.setLayoutManager(manager);
    }
    private void initViews(){
        customtoolbarTop10Book = findViewById(R.id.customtoolbarTop10Book);
        setSupportActionBar(customtoolbarTop10Book);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        customtoolbarTop10Book.setTitleTextColor(Color.WHITE);
        customtoolbarTop10Book.setNavigationIcon(R.drawable.ic_back);
        customtoolbarTop10Book.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        spMonth = findViewById(R.id.spMonth);
        lvListBestBookSelling = findViewById(R.id.lvListBestBookSelling);

    }
}
