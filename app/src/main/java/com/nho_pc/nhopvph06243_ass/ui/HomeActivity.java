package com.nho_pc.nhopvph06243_ass.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.nho_pc.nhopvph06243_ass.R;

public class HomeActivity extends AppCompatActivity {
    private ImageView imgUser;
    private ImageView imgBookType;
    private ImageView imgBook;
    private ImageView imgBill;
    private ImageView imgTopBook;
    private ImageView imgStatistics;
    private ImageView imgIntroduce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setTitle("Trang chủ");
        initViews();


    }

    private void initViews() {
        imgUser = (ImageView) findViewById(R.id.imgUser);
        imgBookType = (ImageView) findViewById(R.id.imgBookType);
        imgBook = (ImageView) findViewById(R.id.imgBook);
        imgBill = (ImageView) findViewById(R.id.imgBill);
        imgTopBook = (ImageView) findViewById(R.id.imgTopBook);
        imgStatistics = (ImageView) findViewById(R.id.imgStatistics);
        imgIntroduce = (ImageView) findViewById(R.id.imgIntroduce);
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
        startActivity(new Intent(HomeActivity.this, TopBookActivity.class));
    }

    public void showStatisticsActivity(View view) {
        startActivity(new Intent(HomeActivity.this, StatisticsActivity.class));
    }
}
