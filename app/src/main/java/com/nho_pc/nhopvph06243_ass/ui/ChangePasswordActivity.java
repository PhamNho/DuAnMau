package com.nho_pc.nhopvph06243_ass.ui;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.nho_pc.nhopvph06243_ass.R;
import com.nho_pc.nhopvph06243_ass.dao.UserDAO;
import com.nho_pc.nhopvph06243_ass.model.Users;

import java.util.Objects;

public class ChangePasswordActivity extends AppCompatActivity {
    private Toolbar customtoolbarChangePassword;
    private TextView edtPassOld, edtPassNew, edtPassNew2;
    private UserDAO userDAO;
    private String strUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        setTitle(getString(R.string.title_changePassword));

        customtoolbarChangePassword = findViewById(R.id.customtoolbarChangePassword);
        setSupportActionBar(customtoolbarChangePassword);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        customtoolbarChangePassword.setNavigationIcon(R.drawable.ic_back);
        customtoolbarChangePassword.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        userDAO = new UserDAO(this);
        edtPassOld = findViewById(R.id.edtPassWord_Old_Dialog_Change_Password);
        edtPassNew = findViewById(R.id.edtPassWord_New_Dialog_Change_Password);
        edtPassNew2 = findViewById(R.id.edtPassWord_New2_Dialog_Change_Password);
        SharedPreferences preferences = getSharedPreferences("USER_FILE2", MODE_PRIVATE);
        strUserName = preferences.getString("UserName", "").toUpperCase();
    }

    public void huyChangePassword(View view) {
        finish();
    }

    public void showChangePassword(View view) {
        String passOld = edtPassOld.getText().toString().trim();
        String passNew = edtPassNew.getText().toString().trim();
        String passNew2 = edtPassNew2.getText().toString().trim();

        if (passNew.isEmpty() || passNew2.isEmpty() || passOld.isEmpty()) {
            if (passOld.isEmpty())
                edtPassOld.setError(getString(R.string.notify_empty_passold));
            if (passNew.isEmpty())
                edtPassNew.setError(getString(R.string.notify_empty_passnew));
            if (passNew.isEmpty())
                edtPassNew2.setError(getString(R.string.notify_empty_passnew2));
        } else if (passNew.length() < 6 || passNew2.length() < 6 || passOld.length() < 6 || passNew.length() > 50 || passNew2.length() > 50 || passOld.length() > 50) {
            if (passNew.length() < 6)
                edtPassNew.setError(getString(R.string.notify_length_pass));
            if (passNew2.length() < 6)
                edtPassNew2.setError(getString(R.string.notify_length_pass));
            if (passOld.length() < 6)
                edtPassOld.setError(getString(R.string.notify_length_pass));
            if (passOld.length() > 50)
                edtPassOld.setError(getString(R.string.notify_length_pass2));
            if (passNew.length() > 50)
                edtPassNew.setError(getString(R.string.notify_length_pass2));
            if (passNew2.length() > 50)
                edtPassNew2.setError(getString(R.string.notify_length_pass2));
        } else if (!passNew.equals(passNew2)) {
            edtPassNew2.setError(getString(R.string.notify_same_pass));
        } else {
            Users user = userDAO.getUser(strUserName);
            if (user.getPassword().equals(passOld)) {
                int result = userDAO.updatePassWord(strUserName, passNew2);
                if (result == 1) {
                    Toast.makeText(getApplicationContext(), getString(R.string.change_pass_success) + " " + strUserName, Toast.LENGTH_SHORT).show();
                } else if (result == -1) {
                    Toast.makeText(getApplicationContext(), getString(R.string.change_pass_failed), Toast.LENGTH_SHORT).show();
                }
            } else {
                edtPassOld.setError(getString(R.string.wrong_pass));
            }
        }
    }
}
