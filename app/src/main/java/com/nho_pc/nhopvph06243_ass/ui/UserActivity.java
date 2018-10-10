package com.nho_pc.nhopvph06243_ass.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.nho_pc.nhopvph06243_ass.R;
import com.nho_pc.nhopvph06243_ass.adapter.UserAdapter;
import com.nho_pc.nhopvph06243_ass.dao.UserDAO;
import com.nho_pc.nhopvph06243_ass.database.DatabaseHelper;
import com.nho_pc.nhopvph06243_ass.model.Users;

import java.util.ArrayList;
import java.util.List;

public class UserActivity extends AppCompatActivity {
      private Toolbar customtoolbarUser;
      public static List<Users> usersList = new ArrayList<>();
      private ListView lvNguoiDung;
      private UserAdapter adapter = null;
      private UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        setTitle("Người dùng");
        customtoolbarUser = (Toolbar) findViewById(R.id.customtoolbarUser);
        setSupportActionBar(customtoolbarUser);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        customtoolbarUser.setTitleTextColor(Color.BLACK);
        customtoolbarUser.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                finish();
            }
        });

        lvNguoiDung = findViewById(R.id.lvUser);
        userDAO=new UserDAO(UserActivity.this);
        usersList=userDAO.getAllUsers();
        adapter = new UserAdapter(this, usersList);
        lvNguoiDung.setAdapter(adapter);

        lvNguoiDung.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(UserActivity.this,usersList.get(position).getName(), Toast.LENGTH_SHORT).show();
            }
        });
        lvNguoiDung.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(UserActivity.this, EditUserActivity.class);
                Bundle b = new Bundle();
                b.putString("USERNAME", usersList.get(position).getUserName().trim());
                b.putString("NAME", usersList.get(position).getName());
                b.putString("PHONE", usersList.get(position).getPhoneNumber());
                intent.putExtras(b);
                startActivity(intent);
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        usersList.clear();
        usersList = userDAO.getAllUsers();
        adapter.changeDataset(userDAO.getAllUsers());
    }

        @Override
        public boolean onCreateOptionsMenu (Menu menu){
            //biến fie menu xml thành giao diện
            getMenuInflater().inflate(R.menu.menu_setting, menu);
            return super.onCreateOptionsMenu(menu);
        }

        @Override
        public boolean onOptionsItemSelected (MenuItem item){
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
                            SharedPreferences pref = getSharedPreferences("USER_FILE",MODE_PRIVATE);
                            SharedPreferences.Editor edit = pref.edit();
                            //xoa tinh trang luu tru truoc do
                            edit.clear();
                            edit.commit();
                            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
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

        public void addUsers (View view){
            startActivity(new Intent(UserActivity.this, AddUserActivity.class));
        }
    }
