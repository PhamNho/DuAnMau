package com.nho_pc.nhopvph06243_ass.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.nho_pc.nhopvph06243_ass.R;

import com.nho_pc.nhopvph06243_ass.dao.UserDAO;
import com.nho_pc.nhopvph06243_ass.database.DatabaseHelper;
import com.nho_pc.nhopvph06243_ass.model.Users;

public class LoginActivity extends AppCompatActivity {
    private EditText edtUser;
    private EditText edtPassword;
    private CheckBox cbRemember;
    private Button btnLogin;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String USERNAME_KEY = "user";
    private String PASSWORD_KEY = "password";

    private UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = edtUser.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();
                if (password.length() < 6 || userName.isEmpty() || password.isEmpty()) {

                    if (userName.isEmpty()) edtUser.setError(getString(R.string.notify_empty_user));

                    if (password.length() < 6) edtPassword.setError(getString(R.string.notify_length_pass));

                    if (password.isEmpty()) edtPassword.setError(getString(R.string.notify_empty_pass));

                } else {

                    // kiem tra user da ton tai trong DB chua !!!
                    Users user;

                    user = userDAO.getUser(edtUser.getText().toString());

                    if (user == null) {
                        Toast.makeText(getApplicationContext(), "User chưa tồn tại !!!", Toast.LENGTH_LONG).show();
                        Users user1 = new Users("admin", "admin123", "Phạm Văn Nhớ", "0962387053");
                        userDAO.insertUser(user1);

                    } else {
                        if (cbRemember.isChecked()) {
                            editor = sharedPreferences.edit();
                            editor.putString(USERNAME_KEY, edtUser.getText().toString().trim());
                            editor.putString(PASSWORD_KEY, edtPassword.getText().toString().trim());
                            editor.commit();
                        }
                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                        Toast.makeText(getApplicationContext(), "Xin chào : "+user.getName(), Toast.LENGTH_LONG).show();
                    }

//                    final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
//                    final int[] a = {0};
//
//                    progressDialog.setTitle("Đang đăng nhập");
//                    progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//
//
//                    CountDownTimer countDownTimer = new CountDownTimer(1500, 40) {
//                        @Override
//                        public void onTick(long millisUntilFinished) {
//                            a[0] = a[0] + 4;
//                            progressDialog.show();
//                            progressDialog.setProgress(a[0]);
//                        }
//
//                        @Override
//                        public void onFinish() {
//                            progressDialog.dismiss();
//                            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
//                        }
//                    }.start();
                }
            }
        });
    }

    private void initViews() {
        userDAO=new UserDAO(getApplicationContext());
        edtUser = (EditText) findViewById(R.id.edtUser);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        cbRemember = (CheckBox) findViewById(R.id.cbRemember);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        sharedPreferences = getSharedPreferences("Input", MODE_PRIVATE);
        edtUser.setText(sharedPreferences.getString(USERNAME_KEY, ""));
        edtPassword.setText(sharedPreferences.getString(PASSWORD_KEY, ""));
    }
}
