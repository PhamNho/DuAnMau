package com.nho_pc.nhopvph06243_ass.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.nho_pc.nhopvph06243_ass.R;
import com.nho_pc.nhopvph06243_ass.dao.UserDAO;
import com.nho_pc.nhopvph06243_ass.model.Users;

public class ChangePasswordActivity extends AppCompatActivity {
    private Toolbar customtoolbarChangePassword;
    private EditText edtNewPassword;
    private EditText edtNewPassword2;
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
        edtNewPassword = (EditText) findViewById(R.id.edtNewPassword);
        edtNewPassword2 = (EditText) findViewById(R.id.edtNewPassword2);
    }

    public void changePassword(View view) {
        String newPassword = edtNewPassword.getText().toString().trim();
        String newPassword2 = edtNewPassword2.getText().toString().trim();
        if (newPassword.isEmpty() || newPassword2.isEmpty()) {
            if (newPassword.isEmpty()) {
                edtNewPassword.setError(getText(R.string.notify_empty_pass_word));
            }
            if (newPassword2.isEmpty()) {
                edtNewPassword2.setError(getText(R.string.notify_empty_pass_word));
            }
        } else if (newPassword.length() < 6 || newPassword2.length() < 6) {

            if (newPassword.length() < 6) {
                edtNewPassword.setError(getText(R.string.notify_length_pass));
            }
            if (newPassword2.length() < 6) {
                edtNewPassword2.setError(getText(R.string.notify_length_pass));
            }
        }else if (!newPassword.equals(newPassword2)){
            edtNewPassword2.setError(getString(R.string.notify_pass_khong_khop));
        }else {
            int result=userDAO.changePasswordUsers(strUserName,newPassword2);
            if (result==1){
                Toast.makeText(this, getString(R.string.doi_pass)+" "+strUserName, Toast.LENGTH_SHORT).show();
            }else if (result==-1){
                Toast.makeText(this, getString(R.string.doi_passtb), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void huyChangePassword(View view) {
        finish();
    }
}
