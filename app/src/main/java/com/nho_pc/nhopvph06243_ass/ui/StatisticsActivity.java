package com.nho_pc.nhopvph06243_ass.ui;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.nho_pc.nhopvph06243_ass.R;
import com.nho_pc.nhopvph06243_ass.dao.StatisticsDAO;
import com.nho_pc.nhopvph06243_ass.database.DatabaseHelper;

import java.util.Objects;

public class StatisticsActivity extends AppCompatActivity {
    private Toolbar customtoolbarStatistics;
    private TextView tvStatisticDay;
    private TextView tvStatisticMonth;
    private TextView tvStatisticYear;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        initView();
        StatisticsDAO statisticDAO = new StatisticsDAO(this);
        tvStatisticDay.setText(statisticDAO.getStatisticByDay()+" VNĐ");
        tvStatisticMonth.setText(statisticDAO.getStatisticByMonth()+" VNĐ");
        tvStatisticYear.setText(statisticDAO.getStatisticByYear()+" VNĐ");

        customtoolbarStatistics.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        customtoolbarStatistics = findViewById(R.id.customtoolbarStatistics);
        setSupportActionBar(customtoolbarStatistics);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tvStatisticDay = findViewById(R.id.tvMoneyToday);
        tvStatisticMonth = findViewById(R.id.tvMoneyMonth);
        tvStatisticYear = findViewById(R.id.tvMoneyYear);

    }
}
