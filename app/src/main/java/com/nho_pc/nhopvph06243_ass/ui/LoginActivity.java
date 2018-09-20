package com.nho_pc.nhopvph06243_ass.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.nho_pc.nhopvph06243_ass.R;

public class LoginActivity extends AppCompatActivity {
    private EditText edtUser;
    private EditText edtPassword;
    private CheckBox cbRemember;
    private Button btnLogin;
    private Button btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
    }

    private void initViews() {
        edtUser = (EditText) findViewById(R.id.edtUser);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        cbRemember = (CheckBox) findViewById(R.id.cbRemember);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnCancel = (Button) findViewById(R.id.btnCancel);

    }

    public void login(View view) {
        String user = edtUser.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        if (user.matches("")) {
            edtUser.setError(getString(R.string.notify_empty_user_name));
            if (password.matches("")) {
                edtPassword.setError(getString(R.string.notify_empty_pass_word));
                return;
            }
        }
        if (password.length() < 6) {
            edtPassword.setError(getString(R.string.notify_min_length_pass));
        }else {
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void huyLogin(View view) {
        finish();
    }
}
