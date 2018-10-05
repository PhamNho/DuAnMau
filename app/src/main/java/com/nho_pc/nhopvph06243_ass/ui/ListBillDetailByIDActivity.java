package com.nho_pc.nhopvph06243_ass.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.nho_pc.nhopvph06243_ass.R;
import com.nho_pc.nhopvph06243_ass.adapter.BillDetailAdapter;
import com.nho_pc.nhopvph06243_ass.dao.BillDetailDAO;
import com.nho_pc.nhopvph06243_ass.model.BillDetail;

import java.util.ArrayList;
import java.util.List;

public class ListBillDetailByIDActivity extends AppCompatActivity {
    public List<BillDetail> billDetailList = new ArrayList<>();
    ListView lvBillDetail;
    BillDetailAdapter adapter = null;
    BillDetailDAO billDetailDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Hóa đơn chi tiết");
        setContentView(R.layout.activity_list_bill_detail_by_id);
        lvBillDetail = (ListView) findViewById(R.id.lvBillDetail);
        billDetailDAO = new BillDetailDAO(ListBillDetailByIDActivity.this);
        Intent in = getIntent();
        Bundle b = in.getExtras();
        if (b != null) {
            billDetailList = billDetailDAO.getAllBillDetailByID(b.getString("MAHOADON"));
        }
        adapter = new BillDetailAdapter(this, billDetailList);
        lvBillDetail.setAdapter(adapter);
    }
}
