package com.nho_pc.nhopvph06243_ass.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.nho_pc.nhopvph06243_ass.R;

public class BillActivity extends AppCompatActivity {
    private Toolbar customtoolbarBill;
    private ListView lvBill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);
        setTitle("Hóa đơn");
        initViews();
        setSupportActionBar(customtoolbarBill);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        customtoolbarBill.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initViews() {
        customtoolbarBill = (Toolbar) findViewById(R.id.customtoolbarBill);
        lvBill = (ListView) findViewById(R.id.lvBill);
    }

    public void addBills(View view) {
        startActivity(new Intent(getApplicationContext(),AddBillActivity.class));
        finish();
    }
}
