package com.nho_pc.nhopvph06243_ass.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nho_pc.nhopvph06243_ass.R;
import com.nho_pc.nhopvph06243_ass.adapter.BillDetailAdapter;
import com.nho_pc.nhopvph06243_ass.dao.BillDetailDAO;
import com.nho_pc.nhopvph06243_ass.dao.BookDAO;
import com.nho_pc.nhopvph06243_ass.model.Bill;
import com.nho_pc.nhopvph06243_ass.model.BillDetail;
import com.nho_pc.nhopvph06243_ass.model.Book;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BillDetailActivity extends AppCompatActivity {
    private Toolbar customtoolbarBillDetail;
    EditText edMaSach, edMaHoaDon, edSoLuong;
    TextView tvThanhTien;
    BillDetailDAO billDetailDAO;
    BookDAO bookDAO;
    public List<BillDetail> billDetailList = new ArrayList<>();
    ListView lvBillDetail;
    BillDetailAdapter detailAdapter = null;
    double thanhTien = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Thêm hóa đơn");
        setContentView(R.layout.activity_bill_detail);
        customtoolbarBillDetail=findViewById(R.id.customtoolbarBillDetail);
        setSupportActionBar(customtoolbarBillDetail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        customtoolbarBillDetail.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        edMaSach = (EditText) findViewById(R.id.edMaSach);
        edMaHoaDon = (EditText) findViewById(R.id.edMaHoaDon);
        edSoLuong = (EditText) findViewById(R.id.edSoLuongMua);
        lvBillDetail = (ListView) findViewById(R.id.lvBillDetail);
        tvThanhTien = (TextView) findViewById(R.id.tvThanhTien);
        detailAdapter = new BillDetailAdapter(this, billDetailList);
        lvBillDetail.setAdapter(detailAdapter);
        Intent in = getIntent();
        Bundle b = in.getExtras();
        if (b != null) {
            edMaHoaDon.setText(b.getString("MAHOADON"));
        }
    }

    public void ADDBillDetail(View view) {
        billDetailDAO = new BillDetailDAO(BillDetailActivity.this);
        bookDAO = new BookDAO(BillDetailActivity.this);
        try {
            if (edMaSach.getText().toString().isEmpty() || edSoLuong.getText().toString().isEmpty() ||
                    edMaHoaDon.getText().toString().isEmpty()) {
                Toast.makeText(getApplicationContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            } else {
                Book book = bookDAO.getBookByID(edMaSach.getText().toString());
                if (book != null) {
                    int pos = checkMaSach(billDetailList, edMaSach.getText().toString());
                    Bill bill = new Bill(edMaHoaDon.getText().toString(), new
                            Date());
                    BillDetail billDetail = new BillDetail(1, bill, book, Integer.parseInt(edSoLuong.getText().toString()));
                    if (pos >= 0) {
                        int soluong = billDetailList.get(pos).getSoLuongMua();
                        billDetail.setSoLuongMua(soluong + Integer.parseInt(edSoLuong.getText().toString()));
                        billDetailList.set(pos, billDetail);
                    } else {
                        billDetailList.add(billDetail);
                    }
                    detailAdapter.changeDataset(billDetailList);
                } else {
                    Toast.makeText(getApplicationContext(), "Mã sách không tồn tại", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (
                Exception ex) {
            Log.e("Error", ex.toString());
        }
    }

    public void thanhToanHoaDon(View view) {
        billDetailDAO = new BillDetailDAO(BillDetailActivity.this);
        //tinh tien
        thanhTien = 0;
        try {
            for (BillDetail hd : billDetailList) {
                billDetailDAO.inserBillDetail(hd);
                thanhTien = thanhTien + hd.getSoLuongMua() * hd.getSach().getGiaBia();
            }
            tvThanhTien.setText("Tổng tiền: " + thanhTien);
        } catch (Exception ex) {
            Log.e("Error", ex.toString());
        }
    }

    public int checkMaSach(List<BillDetail> billDetailList, String maSach) {
        int pos = -1;
        for (int i = 0; i < billDetailList.size(); i++) {
            BillDetail hd = billDetailList.get(i);
            if (hd.getSach().getMaSach().equalsIgnoreCase(maSach)) {
                pos = i;
                break;
            }
        }
        return pos;
    }
}
