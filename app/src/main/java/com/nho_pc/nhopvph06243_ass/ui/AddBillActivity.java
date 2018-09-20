package com.nho_pc.nhopvph06243_ass.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.nho_pc.nhopvph06243_ass.R;

public class AddBillActivity extends AppCompatActivity {
    private Toolbar customtoolbarAddBill;
    private EditText edtBillCode;
    private EditText edtBillDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bill);
        setTitle("Thêm hóa đơn");
        initViews();
        setSupportActionBar(customtoolbarAddBill);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        customtoolbarAddBill.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initViews() {
        customtoolbarAddBill = (Toolbar) findViewById(R.id.customtoolbarAddBill);
        edtBillCode = (EditText) findViewById(R.id.edtBillCode);
        edtBillDate = (EditText) findViewById(R.id.edtBillDate);

    }

    public void addBill(View view) {

    }

    public void huyaddBill(View view) {
        finish();
    }

    public void selectDates(View view) {

    }
}
