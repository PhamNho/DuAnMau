package com.nho_pc.nhopvph06243_ass.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.nho_pc.nhopvph06243_ass.R;

public class HelloActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello);
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(2000);
                }catch (Exception e){

                }finally {
                    Intent iLogin = new Intent(HelloActivity.this,LoginActivity.class);
                    startActivity(iLogin);
                    finish();
                }
            }
        });
        thread.start();
    }
}
