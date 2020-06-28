package com.nho_pc.nhopvph06243_ass.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.nho_pc.nhopvph06243_ass.R;
import com.nho_pc.nhopvph06243_ass.dao.UserDAO;
import com.nho_pc.nhopvph06243_ass.model.Users;

public class LoginActivity extends AppCompatActivity {
    private EditText edtUser;
    private EditText edtPassword;
    private CheckBox cbRemember;
    private UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edtUser = findViewById(R.id.edtUser);
        edtPassword = findViewById(R.id.edtPassword);
        cbRemember = findViewById(R.id.cbRemember);
        Button btnLogin = findViewById(R.id.btnLogin);
        cbRemember.setChecked(true);
        userDAO = new UserDAO(this);
        getUser();

        Users user2 = userDAO.getUser("ADMIN");
        if (user2 == null) {
            userDAO.insertUser("ADMIN", "123456", "Admin", "0962387053");
        }
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = edtUser.getText().toString().trim();
                String pass = edtPassword.getText().toString().trim();
                if (user.isEmpty() || pass.isEmpty()) {
                    if (user.isEmpty()) {
                        edtUser.setError(getString(R.string.notify_empty_user));
                    }
                    if (pass.isEmpty()) {
                        edtPassword.setError(getString(R.string.notify_empty_pass));
                    }
                } else if (pass.length() < 6) {
                    edtPassword.setError(getString(R.string.notify_length_pass));
                } else {
                    Users user2 = userDAO.getUser(edtUser.getText().toString().trim().toUpperCase());
                    if (user2 == null) {
                        edtUser.setError(getString(R.string.user_not_exitst));
                    } else {
                        if (user2.getPassword().equals(edtPassword.getText().toString().trim())) {
                            reUser(edtUser.getText().toString().trim(), edtPassword.getText().toString().trim(), cbRemember.isChecked());
                            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                            Toast.makeText(LoginActivity.this, getText(R.string.hello)+" "+user2.getName(), Toast.LENGTH_SHORT).show();
                        } else {
                            edtPassword.setError(getString(R.string.wrong_pass));
                        }
                    }
                }
            }
        });
    }


    public void huyLogin(View view) {
        finish();
    }

    private void reUser(String u, String p, boolean status) {
        SharedPreferences pref = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        if (!status) {
            edit.clear();
        } else {
            edit.putString("UserName", u);
            edit.putString("PassWord", p);
        }
        edit.apply();
    }

    private void getUser() {
        SharedPreferences pref = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        if (pref != null) {
            String strUserName = pref.getString("UserName", "");
            String strPassWord = pref.getString("PassWord", "");
            edtPassword.setText(strPassWord);
            edtUser.setText(strUserName);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getUser();
    }
}