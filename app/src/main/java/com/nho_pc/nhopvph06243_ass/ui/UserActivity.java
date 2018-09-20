package com.nho_pc.nhopvph06243_ass.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.nho_pc.nhopvph06243_ass.R;

public class UserActivity extends AppCompatActivity {
    private Toolbar customtoolbarUser;
    private ListView lvUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        setTitle("Người dùng");
        customtoolbarUser = (Toolbar) findViewById(R.id.customtoolbarUser);
        lvUser = (ListView) findViewById(R.id.lvUser);

        setSupportActionBar(customtoolbarUser);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        customtoolbarUser.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //biến fie menu xml thành giao diện
        getMenuInflater().inflate(R.menu.menu_setting, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemDoiMatKhau:
                startActivity(new Intent(this, ChangePasswordActivity.class));
                break;
            case R.id.itemDangXuat:
                //B1: định nghĩa alertDialog
                AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
                //B2: thiết lập thông tin
                builder.setTitle("Thông báo");
                builder.setMessage("Bạn có muốn đăng xuất không ?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        finish();
                        Toast.makeText(UserActivity.this, "Đã đăng xuất", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(UserActivity.this, "Đã hủy đăng xuất", Toast.LENGTH_SHORT).show();
                    }
                });
                //B3: hiển thị
                builder.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void addUsers(View view) {
        startActivity(new Intent(UserActivity.this,AddUserActivity.class));
    }
}
