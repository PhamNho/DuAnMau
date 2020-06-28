package com.nho_pc.nhopvph06243_ass.ui;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.nho_pc.nhopvph06243_ass.R;

import java.util.Objects;

public class IntroduceActivity extends AppCompatActivity {
    private Toolbar customtoolbarIntroduce;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduce);
        setTitle(getString(R.string.title_introduce));
        customtoolbarIntroduce = findViewById(R.id.customtoolbarIntroduce);
        setSupportActionBar(customtoolbarIntroduce);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        customtoolbarIntroduce.setTitleTextColor(Color.WHITE);
        customtoolbarIntroduce.setNavigationIcon(R.drawable.ic_back);
        customtoolbarIntroduce.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                finish();
            }
        });
    }
}
