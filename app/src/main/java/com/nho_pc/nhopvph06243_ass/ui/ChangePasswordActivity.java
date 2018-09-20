package com.nho_pc.nhopvph06243_ass.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.nho_pc.nhopvph06243_ass.R;

public class ChangePasswordActivity extends AppCompatActivity {
    private Toolbar customtoolbarChangePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        setTitle("Đổi mật khẩu");
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

    public void huyChangePassword(View view) {
        finish();
    }
}
