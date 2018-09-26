package com.nho_pc.nhopvph06243_ass.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.nho_pc.nhopvph06243_ass.R;
import com.nho_pc.nhopvph06243_ass.dao.UserDAO;
import com.nho_pc.nhopvph06243_ass.model.Users;

public class ChangePasswordActivity extends AppCompatActivity {
    private Toolbar customtoolbarChangePassword;
    private EditText edtPass;
    private EditText edtRePass;
    SharedPreferences preferences;
    String strUserName,strPassword;
    private String USERNAME_KEY = "user";
    private String PASSWORD_KEY = "password";
    private UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        setTitle("Đổi mật khẩu");
        initViews();
        preferences=getSharedPreferences("USER_FILE",MODE_PRIVATE);
        userDAO=new UserDAO(this);
        strUserName=preferences.getString(USERNAME_KEY,"");
        strPassword=preferences.getString(PASSWORD_KEY,"");

        customtoolbarChangePassword = (Toolbar) findViewById(R.id.customtoolbarChangePassword);

        setSupportActionBar(customtoolbarChangePassword);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        customtoolbarChangePassword.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ChangePasswordActivity.this, UserActivity.class));
                finish();
            }
        });
    }

    private void initViews() {
        edtPass = findViewById(R.id.edtNewPassword);
        edtRePass = findViewById(R.id.edtNewPassword2);
    }

    public int validateForm(){
        int check = 1;
        if (edtPass.getText().length()==0 || edtRePass.getText().length() == 0) {
            if (edtPass.getText().length()==0 )edtPass.setError(getText(R.string.notify_empty_pass));
            if (edtRePass.getText().length()==0 )edtRePass.setError(getText(R.string.notify_empty_pass));
            check = -1;
        }else {
            String pass = edtPass.getText().toString();
            String rePass = edtRePass.getText().toString();
            if (!pass.equals(rePass)) {
                Toast.makeText(getApplicationContext(), "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
                check = -1;
            }
        }
        return check;
    }
    public void changePassword(View view) {
        SharedPreferences pref = getSharedPreferences("USER_FILE",MODE_PRIVATE);
        String strUserName = pref.getString("USERNAME","");
        userDAO = new UserDAO(ChangePasswordActivity.this);
        Users user = new Users(strUserName, edtPass.getText().toString(), "", "");
        try {
            if (validateForm()>0){
                if (userDAO.changePasswordUsers(user) > 0) {
                    Toast.makeText(getApplicationContext(), "Lưu thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Lưu thất bại", Toast.LENGTH_SHORT).show();
                }
            }
            finish();
        } catch (Exception ex) {
            Log.e("Error", ex.toString());
        }
    }

    public void huyChangePassword(View view) {
        finish();
    }
}
