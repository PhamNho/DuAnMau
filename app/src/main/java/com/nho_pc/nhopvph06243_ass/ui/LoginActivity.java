package com.nho_pc.nhopvph06243_ass.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.nho_pc.nhopvph06243_ass.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void login(View view) {
        Intent intent=new Intent(LoginActivity.this,HomeActivity.class);
        startActivity(intent);
        finish();
    }

    public void huyLogin(View view) {
        finish();
    }
}
