package com.nho_pc.nhopvph06243_ass.ui;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.nho_pc.nhopvph06243_ass.R;
import com.nho_pc.nhopvph06243_ass.dao.BillDAO;
import com.nho_pc.nhopvph06243_ass.model.Bill;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class AddBillActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private Toolbar customtoolbarAddBill;
    private EditText edtBillCode;
    private EditText edtBillDate;
    private BillDAO billDAO;
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

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
        billDAO = new BillDAO(AddBillActivity.this);
        try {
            if (validation() < 0) {
                Toast.makeText(getApplicationContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            } else {
                Bill bill = new Bill(edtBillCode.getText().toString(), sdf.parse(edtBillDate.getText().toString()));
                if (billDAO.inserBill(bill) > 0) {
                    Toast.makeText(getApplicationContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddBillActivity.this, BillDetailActivity.class);
                    Bundle b = new Bundle();
                    b.putString("MAHOADON", edtBillCode.getText().toString());
                    intent.putExtras(b);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception ex) {
        }
    }

    public void huyaddBill(View view) {
        finish();
    }

    public void selectDates(View view) {
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.show(getFragmentManager(), "date");
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        Calendar cal = new GregorianCalendar(year, month, day);
        setDate(cal);
    }

    private void setDate(final Calendar calendar) {
        edtBillDate.setText(sdf.format(calendar.getTime()));
    }

    public static class DatePickerFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), (DatePickerDialog.OnDateSetListener) getActivity(), year, month, day);
        }
    }

    public int validation() {
        if
                (edtBillCode.getText().toString().isEmpty() || edtBillDate.getText().toString().isEmpty()
                ) {
            return -1;
        }
        return 1;
    }
}
