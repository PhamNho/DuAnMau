package com.nho_pc.nhopvph06243_ass.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.nho_pc.nhopvph06243_ass.R;

public class StatisticsActivity extends AppCompatActivity {
    private Toolbar customtoolbarStatistics;
    private TextView tvMoneyToday;
    private TextView tvMoneyMonth;
    private TextView tvMoneyYear;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        setTitle("Thống kê");
        initViews();
        setSupportActionBar(customtoolbarStatistics);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        customtoolbarStatistics.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initViews() {
        customtoolbarStatistics = (Toolbar) findViewById(R.id.customtoolbarStatistics);
        tvMoneyToday = (TextView) findViewById(R.id.tvMoneyToday);
        tvMoneyMonth = (TextView) findViewById(R.id.tvMoneyMonth);
        tvMoneyYear = (TextView) findViewById(R.id.tvMoneyYear);
    }
}
