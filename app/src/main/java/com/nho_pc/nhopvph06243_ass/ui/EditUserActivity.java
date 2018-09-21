package com.nho_pc.nhopvph06243_ass.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.nho_pc.nhopvph06243_ass.R;

public class EditUserActivity extends AppCompatActivity {
    private Toolbar customtoolbarEditUser;
    private EditText edtEditName;
    private EditText edtEditPhoneNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
        setTitle("Sửa thông tin người dùng");
        customtoolbarEditUser = (Toolbar) findViewById(R.id.customtoolbarEditUser);
        edtEditName = (EditText) findViewById(R.id.edtEditName);
        edtEditPhoneNumber = (EditText) findViewById(R.id.edtEditPhoneNumber);

        setSupportActionBar(customtoolbarEditUser);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        customtoolbarEditUser.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), UserActivity.class));
                finish();
            }
        });
    }

    public void editUser(View view) {

    }

    public void huyEditUser(View view) {
        finish();
    }
}
