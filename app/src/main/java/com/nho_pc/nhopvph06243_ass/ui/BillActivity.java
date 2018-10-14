package com.nho_pc.nhopvph06243_ass.ui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.nho_pc.nhopvph06243_ass.R;
import com.nho_pc.nhopvph06243_ass.adapter.BillAdapter;
import com.nho_pc.nhopvph06243_ass.dao.BillDAO;
import com.nho_pc.nhopvph06243_ass.dao.BillDetailDAO;
import com.nho_pc.nhopvph06243_ass.listener.OnClick;
import com.nho_pc.nhopvph06243_ass.listener.OnDelete;
import com.nho_pc.nhopvph06243_ass.listener.OnEdit;
import com.nho_pc.nhopvph06243_ass.model.Bill;
import com.nho_pc.nhopvph06243_ass.model.BillDetail;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class BillActivity extends AppCompatActivity implements OnDelete, OnClick, OnEdit {
    private Toolbar toolbarBill;
    private RecyclerView rvListBill;
    private List<Bill> billList;
    private BillAdapter billAdapter;
    private FloatingActionButton fbtnAddBill;
    private BillDAO billDAO;
    private BillDetailDAO billDetailDAO;
    private long date = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);
        setTitle(getString(R.string.title_bill));

        initViews();
        initAction();
        billDetailDAO = new BillDetailDAO(this);
        billDAO = new BillDAO(this);
        billList = billDAO.getAllBills();
        Collections.reverse(billList);
        billAdapter = new BillAdapter(billList, this, this, this);
        rvListBill.setAdapter(billAdapter);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        rvListBill.setLayoutManager(manager);
    }

    private void initAction() {
        fbtnAddBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogAddBill();
            }
        });

        toolbarBill.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        rvListBill.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && fbtnAddBill.getVisibility() == View.VISIBLE) {
                    fbtnAddBill.hide();

                } else if (dy < 0 && fbtnAddBill.getVisibility() != View.VISIBLE) {
                    fbtnAddBill.show();

                }
            }
        });
    }

    private void initViews() {
        toolbarBill = findViewById(R.id.customtoolbarBill);
        setSupportActionBar(toolbarBill);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        toolbarBill.setTitleTextColor(Color.WHITE);
        toolbarBill.setTitle(getString(R.string.title_bill));
        toolbarBill.setNavigationIcon(R.drawable.ic_back);
        fbtnAddBill = findViewById(R.id.fbtn_Bill);
        rvListBill = findViewById(R.id.rvBill);
        rvListBill.setHasFixedSize(true);
    }

    private void showDialogAddBill() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") View dialogView = Objects.requireNonNull(inflater).inflate(R.layout.dialog_add_bill, null);
        builder.setView(dialogView);
        final Dialog dialog = builder.show();

        Button Add = dialogView.findViewById(R.id.btnAddBill_Dialog_Add_Bill);
        Button Cancel = dialogView.findViewById(R.id.btnCancelBill_Dialog_Add_Bill);
        final EditText edtBillID = dialogView.findViewById(R.id.edtBillCode);
        final EditText edtDate_Add_Bill = dialogView.findViewById(R.id.edtBillDate);
        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String billID = edtBillID.getText().toString().trim().toUpperCase();

                if (date < 0)
                    Toast.makeText(BillActivity.this, R.string.pick_date, Toast.LENGTH_SHORT).show();
                else if (billID.isEmpty())
                    edtBillID.setError(getString(R.string.notify_empty_BillID));

                else if (billID.length() > 7) {
                    edtBillID.setError(getString(R.string.notify_max_bill_id));
                } else {
                    Boolean check = checkKeyPrimary(billID);
                    if (check) {
                        Bill bill = new Bill(billID, date);
                        long result = billDAO.insertBill(bill);
                        if (result > 0) {
                            billList = billDAO.getAllBills();
                            billAdapter.changeDataset(billList);
                            Collections.reverse(billList);
                            date = -1;
                            Toast.makeText(BillActivity.this, getString(R.string.addBill) + " " + billID, Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            Intent intent = new Intent(BillActivity.this, BillDetailActivity.class);
                            intent.putExtra("ID", billID);
                            startActivity(intent);
                        } else {
                            Toast.makeText(BillActivity.this, getString(R.string.add_failed_bill), Toast.LENGTH_SHORT).show();
                            date = -1;
                            dialog.dismiss();

                        }
                    } else {
                        edtBillID.setError(getString(R.string.notify_BillID_exists));
                    }
                }
            }

        });

        Button pickDate = dialogView.findViewById(R.id.btnPickDate_Dialog_Add_Bill);

        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date = -1;
                dialog.dismiss();
            }
        });
        pickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(edtDate_Add_Bill);
            }
        });
    }
    private boolean checkKeyPrimary(String ID) {
        Bill bill = billDAO.getBillByID(ID);
        return bill == null;
    }

    @Override
    public void onClick(String billID) {
        Intent intent = new Intent(this, BillDetailActivity.class);
        intent.putExtra("ID", billID);
        startActivity(intent);
    }

    @Override
    public void onDelete(final int position) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(getString(R.string.delete_message_bill) + billList.get(position).getBill_ID() + " ?");
        builder.setNegativeButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                List<BillDetail> billDetail = billDetailDAO.getAllBillDetailsByBillID(billList.get(position).getBill_ID());
                if (billDetail.size() > 0) {
                    Toast.makeText(BillActivity.this, getString(R.string.delete_billDetail), Toast.LENGTH_SHORT).show();
                } else {
                    long result = billDAO.deleteBill(billList.get(position).getBill_ID());
                    if (result > 0) {
                        Toast.makeText(BillActivity.this, getString(R.string.deleted) + " " + billList.get(position).getBill_ID(), Toast.LENGTH_SHORT).show();
                        billList = billDAO.getAllBills();
                        billAdapter.changeDataset(billList);
                        Collections.reverse(billList);
                    } else {
                        Toast.makeText(BillActivity.this, getString(R.string.deleted_failed), Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
        builder.setPositiveButton(getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    @Override
    public void onEdit(final int position) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") View dialogView = Objects.requireNonNull(inflater).inflate(R.layout.dialog_edit_bill, null);
        builder.setView(dialogView);
        final Dialog dialog = builder.show();

        Button btnEdit = dialogView.findViewById(R.id.btnEdit_Dialog_Edit_Bill);
        Button btnCancel = dialogView.findViewById(R.id.btnCancel_Dialog_Edit_Bill);
        Button pickDate = dialogView.findViewById(R.id.btnPickDate_Dialog_Edit_Bill);
        final EditText edtDate_Edit = dialogView.findViewById(R.id.edtDate_Dialog_Edit_Bill);

        edtDate_Edit.setText(new Date(billList.get(position).getDate()).toString());

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (date < 0) {
                    Toast.makeText(BillActivity.this, getString(R.string.pick_date), Toast.LENGTH_SHORT).show();
                } else {
                    Bill bill = new Bill(billList.get(position).getBill_ID(), date);
                    long result = billDAO.updateBill(bill);
                    if (result > 0) {
                        Toast.makeText(BillActivity.this, getString(R.string.editedBill) + " " + billList.get(position).getBill_ID(), Toast.LENGTH_SHORT).show();
                        billList = billDAO.getAllBills();
                        billAdapter.changeDataset(billList);
                        Collections.reverse(billList);
                        dialog.dismiss();
                        date = -1;
                    } else {
                        Toast.makeText(BillActivity.this, getString(R.string.edited_faied), Toast.LENGTH_SHORT).show();
                        date = -1;
                        dialog.dismiss();
                    }
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                date = -1;
            }
        });
        pickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(edtDate_Edit);
            }
        });
    }
    private void showDatePicker(final TextView editText) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, dayOfMonth);
                date = calendar.getTimeInMillis();
                editText.setText(new Date(date).toString());
            }
        }, year, month, day);
        datePickerDialog.show();
    }
}
