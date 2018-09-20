package com.nho_pc.nhopvph06243_ass.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.nho_pc.nhopvph06243_ass.R;

public class AddUserActivity extends AppCompatActivity {
    private Toolbar customtoolbarAddUser;
    private EditText edtUserName;
    private EditText edtPasswordInAddUser;
    private EditText edtChangePasswordInAddUser;
    private EditText edtPhoneNumberInAddUser;
    private EditText edtName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        setTitle("Thêm người dùng");
        initViews();
        setSupportActionBar(customtoolbarAddUser);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        customtoolbarAddUser.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initViews() {
        customtoolbarAddUser = (Toolbar) findViewById(R.id.customtoolbarAddUser);
        edtUserName = (EditText) findViewById(R.id.edtUserName);
        edtPasswordInAddUser = (EditText) findViewById(R.id.edtPasswordInAddUser);
        edtChangePasswordInAddUser = (EditText) findViewById(R.id.edtChangePasswordInAddUser);
        edtPhoneNumberInAddUser = (EditText) findViewById(R.id.edtPhoneNumberInAddUser);
        edtName = (EditText) findViewById(R.id.edtName);

    }

    public void huyChangePassword(View view) {
        finish();
    }

    public void addUser(View view) {

    }

    public void huyaddUser(View view) {
        startActivity(new Intent(getApplicationContext(),UserActivity.class));
        finish();
        Toast.makeText(this, "Hủy thêm người dùng", Toast.LENGTH_SHORT).show();
    }
}
