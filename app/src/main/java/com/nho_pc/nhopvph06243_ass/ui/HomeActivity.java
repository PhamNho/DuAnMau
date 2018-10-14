package com.nho_pc.nhopvph06243_ass.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.nho_pc.nhopvph06243_ass.R;

public class HomeActivity extends AppCompatActivity {
    ImageView imgUser;
    ImageView imgBookType;
    ImageView imgBook;
    ImageView imgBill;
    ImageView imgTopBook;
    ImageView imgStatistics;
    ImageView imgIntroduce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setTitle(getString(R.string.title_home));
        initViews();


    }

    private void initViews() {
        imgUser = findViewById(R.id.imgUser);
        imgBookType = findViewById(R.id.imgBookType);
        imgBook = findViewById(R.id.imgBook);
        imgBill = findViewById(R.id.imgBill);
        imgTopBook = findViewById(R.id.imgTopBook);
        imgStatistics = findViewById(R.id.imgStatistics);
        imgIntroduce = findViewById(R.id.imgIntroduce);
    }

    public void showUserActivity(View view) {
        startActivity(new Intent(HomeActivity.this, UserActivity.class));
    }

    public void showIntroduceActivity(View view) {
        startActivity(new Intent(HomeActivity.this, IntroduceActivity.class));
    }

    public void showBookActivity(View view) {
        startActivity(new Intent(HomeActivity.this, BookActivity.class));
    }

    public void showBookTypeActivity(View view) {
        startActivity(new Intent(HomeActivity.this, BookTypeActivity.class));
    }

    public void showBillActivity(View view) {
        startActivity(new Intent(HomeActivity.this, BillActivity.class));
    }

    public void showTopBookActivity(View view) {
        startActivity(new Intent(HomeActivity.this, Top10BookActivity.class));
    }

    public void showStatisticsActivity(View view) {
        startActivity(new Intent(HomeActivity.this, StatisticsActivity.class));
    }
}
