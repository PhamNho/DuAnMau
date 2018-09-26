package com.nho_pc.nhopvph06243_ass.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nho_pc.nhopvph06243_ass.R;
import com.nho_pc.nhopvph06243_ass.dao.UserDAO;
import com.nho_pc.nhopvph06243_ass.model.Users;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class EditUserActivity extends AppCompatActivity {
    private Toolbar customtoolbarEditUser;
    private EditText edtEditName;
    private EditText edtEditPhoneNumber;
    private UserDAO userDAO;
    private List<Users> usersList;
    private Button btnEditUsser;
    String username,name,phonenumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
        setTitle("Sửa thông tin người dùng");
        initViews();

        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        username=bundle.getString("USERNAME");
        name=bundle.getString("NAME");
        phonenumber=bundle.getString("PHONE");
        edtEditName.setText(name);
        edtEditPhoneNumber.setText(phonenumber);
        btnEditUsser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userDAO.updateInfoUsers(username,edtEditName.getText().toString(),edtEditPhoneNumber.getText().toString())>0){
                    startActivity(new Intent(getApplicationContext(),UserActivity.class));
                    Toast.makeText(getApplicationContext(),"Lưu thành công",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initViews() {
        customtoolbarEditUser = (Toolbar) findViewById(R.id.customtoolbarEditUser);
        edtEditName = (EditText) findViewById(R.id.edtEditName);
        edtEditPhoneNumber = (EditText) findViewById(R.id.edtEditPhoneNumber);
        btnEditUsser = (Button) findViewById(R.id.btnEditUsser);
        userDAO=new UserDAO(this);
        usersList=new ArrayList<>();

        setSupportActionBar(customtoolbarEditUser);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        customtoolbarEditUser.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), UserActivity.class));
                finish();
            }
        });
    }
    public void huyEditUser(View view) {
        finish();
    }
}
