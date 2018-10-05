package com.nho_pc.nhopvph06243_ass.ui;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ListView;

import com.nho_pc.nhopvph06243_ass.R;
import com.nho_pc.nhopvph06243_ass.adapter.BillAdapter;
import com.nho_pc.nhopvph06243_ass.dao.BillDAO;
import com.nho_pc.nhopvph06243_ass.model.Bill;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class BillActivity extends AppCompatActivity {
    private Toolbar customtoolbarBill;
    private ListView lvBill;
    private List<Bill> billList=new ArrayList<>();
    private BillDAO billDAO;
    private BillAdapter billAdapter=null;
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
        billDAO=new BillDAO(BillActivity.this);
        try {
            billList=billDAO.getAllBill();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        billAdapter = new BillAdapter(this, billList);
        lvBill.setAdapter(billAdapter);
    }
    @Override
    protected void onResume() {
        super.onResume();
        billList.clear();
        try {
            billList = billDAO.getAllBill();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            billAdapter.changeDataset(billDAO.getAllBill());
        } catch (ParseException e) {
            e.printStackTrace();
        }
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
