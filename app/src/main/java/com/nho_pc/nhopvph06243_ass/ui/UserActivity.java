package com.nho_pc.nhopvph06243_ass.ui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nho_pc.nhopvph06243_ass.R;
import com.nho_pc.nhopvph06243_ass.adapter.UserAdapter;
import com.nho_pc.nhopvph06243_ass.dao.UserDAO;
import com.nho_pc.nhopvph06243_ass.database.DatabaseHelper;
import com.nho_pc.nhopvph06243_ass.listener.OnDelete;
import com.nho_pc.nhopvph06243_ass.listener.OnEdit;
import com.nho_pc.nhopvph06243_ass.model.Users;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserActivity extends AppCompatActivity implements OnEdit, OnDelete {
    private Toolbar customtoolbarUser;
    private RecyclerView rvListUser;
    private List<Users> userList;
    private UserAdapter userAdapter;
    private UserDAO userDAO;
    private final String format_phone = "0\\d{9,10}";
    private String strUserName;
    private FloatingActionButton fbtnAddUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        setTitle(getString(R.string.title_user));
        setTitleColor(Color.BLACK);
        initViews();
        SharedPreferences preferences = getSharedPreferences("USER_FILE2", MODE_PRIVATE);
        strUserName = preferences.getString("UserName", "").toUpperCase();
        userDAO = new UserDAO(this);
        customtoolbarUser.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        userList = new ArrayList<>();
        userAdapter = new UserAdapter(userList, this, this);
        rvListUser.setAdapter(userAdapter);
        getUser();
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        rvListUser.setLayoutManager(manager);
        fbtnAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogAddUser();
            }
        });

    }

    private void initViews() {
        customtoolbarUser = findViewById(R.id.customtoolbarUser);
        rvListUser = findViewById(R.id.rvUser);
        setSupportActionBar(customtoolbarUser);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        customtoolbarUser.setTitleTextColor(Color.WHITE);
        customtoolbarUser.setTitle(getString(R.string.title_user));
        customtoolbarUser.setNavigationIcon(R.drawable.ic_back);
        fbtnAddUser=findViewById(R.id.fbtn_User);
    }

    private void getUser() {
        Cursor cursorUser = userDAO.getUser();
        if (cursorUser == null) {
            return;
        }
        if (cursorUser.moveToFirst()) {
            do {
                Users user = new Users();
                user.setUserName(cursorUser.getString(cursorUser.getColumnIndex(DatabaseHelper.COLUMN_USERNAME)));
                user.setPassword(cursorUser.getString(cursorUser.getColumnIndex(DatabaseHelper.COLUMN_PASSWORD)));
                user.setName(cursorUser.getString(cursorUser.getColumnIndex(DatabaseHelper.COLUMN_NAME)));
                user.setPhoneNumber(cursorUser.getString(cursorUser.getColumnIndex(DatabaseHelper.COLUMN_PHONE_NUMBER)));
                userList.add(user);
            } while (cursorUser.moveToNext());
        }
        cursorUser.close();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemDangXuat:
                //B1: định nghĩa alertDialog
                AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
                //B2: thiết lập thông tin
                builder.setTitle(getString(R.string.title_dialog_sign_out));
                builder.setMessage(getString(R.string.message_dialog_sign_out));
                builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences pref = getSharedPreferences("USER_FILE", MODE_PRIVATE);
                        SharedPreferences.Editor edit = pref.edit();
                        //xoa tinh trang luu tru truoc do
                        edit.clear();
                        edit.apply();
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        finish();
                        Toast.makeText(UserActivity.this, "Đã đăng xuất", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(UserActivity.this, "Đã hủy đăng xuất", Toast.LENGTH_SHORT).show();
                    }
                });
                //B3: hiển thị
                builder.show();
                break;
            case R.id.itemDoiMatKhau:
                startActivity(new Intent(UserActivity.this,ChangePasswordActivity.class));
                break;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_setting, menu);
        return true;
    }
    private void showDialogAddUser() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") View dialogView = Objects.requireNonNull(inflater).inflate(R.layout.dialog_add_user, null);
        builder.setView(dialogView);
        final Dialog dialog = builder.show();

        final EditText edUserName = dialogView.findViewById(R.id.edtUserName);
        final EditText edPassWord = dialogView.findViewById(R.id.edtPasswordInAddUser);
        final EditText edPassWord2 = dialogView.findViewById(R.id.edtChangePasswordInAddUser);
        final EditText edPhone = dialogView.findViewById(R.id.edtPhoneNumberInAddUser);
        final EditText edName = dialogView.findViewById(R.id.edtName);
        Button btnAdd = dialogView.findViewById(R.id.btnAddUser_Dialog_Add_User);
        Button btnCancel = dialogView.findViewById(R.id.btnCancel_Dialog_Add_User);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = edUserName.getText().toString().trim().toUpperCase();
                String passWord = edPassWord.getText().toString().trim();
                String passWord2 = edPassWord2.getText().toString().trim();
                String name = edName.getText().toString().trim();
                String phone = edPhone.getText().toString().trim();

                if (userName.isEmpty() || passWord.isEmpty() || passWord2.isEmpty() || name.isEmpty() || phone.isEmpty()) {
                    if (userName.isEmpty())
                        edUserName.setError(getString(R.string.notify_empty_user));
                    if (passWord.isEmpty())
                        edPassWord.setError(getString(R.string.notify_empty_pass));
                    if (passWord2.isEmpty())
                        edPassWord2.setError(getString(R.string.notify_empty_pass2));
                    if (name.isEmpty())
                        edName.setError(getString(R.string.notify_empty_name));
                    if (phone.isEmpty())
                        edPhone.setError(getString(R.string.notify_empty_sdt));
                } else if (passWord2.length() < 6 || passWord.length() < 6 || userName.length() > 50 || passWord.length() > 50 || passWord2.length() > 50 || name.length() > 50) {
                    if (passWord.length() < 6)
                        edPassWord.setError(getString(R.string.notify_length_pass));
                    if (passWord2.length() < 6)
                        edPassWord2.setError(getString(R.string.notify_length_pass));
                    if (userName.length() > 50)
                        edUserName.setError(getString(R.string.notify_length_user));
                    if (passWord.length() > 50)
                        edPassWord.setError(getString(R.string.notify_length_pass2));
                    if (passWord2.length() > 50)
                        edPassWord2.setError(getString(R.string.notify_length_pass2));
                    if (name.length() > 50)
                        edName.setError(getString(R.string.notify_length_name));
                } else if (!passWord.equals(passWord2)) {
                    edPassWord2.setError(getString(R.string.notify_same_pass));
                } else if (!phone.matches(format_phone)) {
                    edPhone.setError(getString(R.string.notify_same_sdt));
                } else {
                    Users user2 = userDAO.getUser(userName);
                    if (user2 == null) {
                        if (strUserName.equals(getString(R.string.admin))) {
                            Integer a = userDAO.insertUser(userName, passWord, name, phone);
                            if (a == 1) {
                                Toast.makeText(UserActivity.this, getString(R.string.addUserName) + " " + userName, Toast.LENGTH_SHORT).show();
                                userList.clear();
                                getUser();
                                userAdapter.notifyDataSetChanged();
                            } else if (a == -1) {
                                Toast.makeText(UserActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();
                            }
                            dialog.dismiss();
                        } else {
                            dialog.dismiss();
                            Toast.makeText(UserActivity.this, getString(R.string.permision), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        edUserName.setError(getString(R.string.user_exits));
                    }
                }
            }
        });
    }

    @Override
    public void onDelete(final int position) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(getString(R.string.question_delete_user)+" "+userList.get(position).getName()+" ?");
        builder.setNegativeButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (userList.get(position).getUserName().equals(getString(R.string.admin))) {
                    Toast.makeText(UserActivity.this, getString(R.string.cannot_delete_admin), Toast.LENGTH_SHORT).show();
                } else {
                    if (strUserName.equals(getString(R.string.admin))) {
                        int result = userDAO.deleteUser(userList.get(position).getUserName());
                        if (result == 1) {
                            Toast.makeText(UserActivity.this, getString(R.string.deleted_user) + " " + userList.get(position).getUserName(), Toast.LENGTH_SHORT).show();
                            userList.clear();
                            getUser();
                            userAdapter.notifyDataSetChanged();
                        } else if (result == -1) {
                            Toast.makeText(UserActivity.this, getString(R.string.deleted_failed), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(UserActivity.this, getString(R.string.permision), Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
        builder.setPositiveButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    @Override
    public void onEdit(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") View dialogView = Objects.requireNonNull(inflater).inflate(R.layout.dialog_edit_user, null);
        builder.setView(dialogView);
        final Dialog dialog = builder.show();
        final EditText edName = dialogView.findViewById(R.id.edtUserName_Dialog_Edit_User);
        final EditText edPhone = dialogView.findViewById(R.id.edtPhoneNumber_Dialog_Edit_User);
        String name_old = userList.get(position).getName();
        String phone_old = userList.get(position).getPhoneNumber();
        edName.setText(name_old);
        edPhone.setText(phone_old);


        Button btnEdit = dialogView.findViewById(R.id.btnEdit_Dialog_Edit_User);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name_new = edName.getText().toString().trim();
                final String phone_new = edPhone.getText().toString();
                if (name_new.isEmpty() || phone_new.isEmpty()) {
                    if (name_new.isEmpty()) {
                        edName.setError(getString(R.string.notify_empty_name));
                    }
                    if (phone_new.isEmpty()) {
                        edPhone.setError(getString(R.string.notify_empty_sdt));
                    }
                } else if (name_new.length() > 50) {
                    edName.setError(getString(R.string.notify_length_name));
                } else if (!phone_new.matches(format_phone)) {
                    edPhone.setError(getString(R.string.notify_same_sdt));
                } else {
                    if (strUserName.equals(getString(R.string.admin))) {
                        int result = userDAO.updateUser(userList.get(position).getUserName(), name_new, phone_new);
                        if (result == 1) {
                            Toast.makeText(UserActivity.this, getString(R.string.editedUser) + " " + userList.get(position).getUserName(), Toast.LENGTH_SHORT).show();
                            userList.clear();
                            getUser();
                            userAdapter.notifyDataSetChanged();
                        } else if (result == -1) {
                            Toast.makeText(UserActivity.this, getString(R.string.edited_faied), Toast.LENGTH_SHORT).show();
                        }
                        dialog.dismiss();
                    } else {
                        dialog.dismiss();
                        Toast.makeText(UserActivity.this, getString(R.string.permision), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        Button btnCancel = dialogView.findViewById(R.id.btnCancel_Dialog_Edit_User);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}

