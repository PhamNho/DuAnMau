package com.nho_pc.nhopvph06243_ass.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.nho_pc.nhopvph06243_ass.R;
import com.nho_pc.nhopvph06243_ass.database.DatabaseHelper;
import com.nho_pc.nhopvph06243_ass.model.Users;

public class AddUserActivity extends AppCompatActivity {
    private Toolbar customtoolbarAddUser;
    private EditText edtUserName;
    private EditText edtPasswordInAddUser;
    private EditText edtChangePasswordInAddUser;
    private EditText edtPhoneNumberInAddUser;
    private EditText edtName;

    private DatabaseHelper databaseHelper;

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

        databaseHelper=new DatabaseHelper(this);

    }

    public void addUser(View view) {

        String username=edtUserName.getText().toString().trim();
        String password=edtPasswordInAddUser.getText().toString().trim();
        String changepassword=edtChangePasswordInAddUser.getText().toString().trim();
        String phonenumber=edtPhoneNumberInAddUser.getText().toString().trim();
        String name=edtName.getText().toString().trim();

        if (username.isEmpty()||password.isEmpty()||changepassword.isEmpty()||phonenumber.isEmpty()||name.isEmpty()){
            if (username.isEmpty())edtUserName.setError(getString(R.string.notify_empty_user_name));
            if (password.isEmpty())edtPasswordInAddUser.setError(getString(R.string.notify_empty_pass_word));
            if (changepassword.isEmpty())edtChangePasswordInAddUser.setError(getString(R.string.notify_empty_pass_word));
            if (password!=changepassword)edtChangePasswordInAddUser.setError("Mật khẩu không khớp !");
            if (phonenumber.isEmpty())edtPhoneNumberInAddUser.setError(getString(R.string.notify_empty_pass_word));
            if (name.isEmpty())edtName.setError(getString(R.string.notify_empty_pass_word));
        }else {
            Users users=new Users(username,password,name,phonenumber);
            databaseHelper.insertUser(users);
            Toast.makeText(this, "Đã thêm", Toast.LENGTH_SHORT).show();
        }

    }

    public void huyaddUser(View view) {
        startActivity(new Intent(getApplicationContext(),UserActivity.class));
        finish();
        Toast.makeText(this, "Hủy thêm người dùng", Toast.LENGTH_SHORT).show();
    }
}
