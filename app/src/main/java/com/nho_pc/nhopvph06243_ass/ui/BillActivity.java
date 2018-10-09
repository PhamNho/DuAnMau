package com.nho_pc.nhopvph06243_ass.ui;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
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
    private List<Bill> billList = new ArrayList<>();
    private BillDAO billDAO;
    private BillAdapter billAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);
        setTitle("Hóa đơn");
        customtoolbarBill=findViewById(R.id.customtoolbarBill);
        setSupportActionBar(customtoolbarBill);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        customtoolbarBill.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        lvBill = (ListView) findViewById(R.id.lvBill);
        billDAO = new BillDAO(BillActivity.this);
        try {
            billList = billDAO.getAllBill();
            Log.e("ABC",billList.size()+"");
        } catch (Exception e) {
            Log.d("Error: ", e.toString());
        }
        billAdapter = new BillAdapter(this, billList);
        lvBill.setAdapter(billAdapter);
        lvBill.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bill bill = (Bill) parent.getItemAtPosition(position);
                Intent intent = new Intent(BillActivity.this, ListBillDetailByIDActivity.class);
                Bundle b = new Bundle();
                b.putString("MAHOADON", bill.getMaHoaDon());
                intent.putExtras(b);
                startActivity(intent);
            }
        });
        // TextFilter
        lvBill.setTextFilterEnabled(true);
        EditText edSeach = (EditText) findViewById(R.id.edtSearch);
        edSeach.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                System.out.println("Text [" + s + "] - Start [" + start + "] - Before[" + before + "] - Count[" + count + "]");
                if (count < before) {
                    billAdapter.resetData();
                }
                billAdapter.getFilter().filter(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    public void addBills(View view) {
        startActivity(new Intent(getApplicationContext(), AddBillActivity.class));
        finish();
    }
}
