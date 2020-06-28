package com.nho_pc.nhopvph06243_ass.ui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nho_pc.nhopvph06243_ass.R;
import com.nho_pc.nhopvph06243_ass.adapter.BookTypeAdapter;
import com.nho_pc.nhopvph06243_ass.dao.BookDAO;
import com.nho_pc.nhopvph06243_ass.dao.BookTypeDAO;
import com.nho_pc.nhopvph06243_ass.listener.OnDelete;
import com.nho_pc.nhopvph06243_ass.listener.OnEdit;
import com.nho_pc.nhopvph06243_ass.model.Book;
import com.nho_pc.nhopvph06243_ass.model.BookType;

import java.util.List;
import java.util.Objects;

public class BookTypeActivity extends AppCompatActivity implements OnDelete, OnEdit {

    private Toolbar customToolBarBook;
    private FloatingActionButton fabAddTypeBook;
    private RecyclerView rvListTypeBook;
    private List<BookType> typeBookList;
    private BookTypeAdapter bookTypeAdapter;
    private BookTypeDAO bookTypeDAO;
    private BookDAO bookDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_type);
        inisViews();
        initAction();
        bookTypeDAO = new BookTypeDAO(this);
        typeBookList = bookTypeDAO.getAllTypeBooks();
        bookDAO = new BookDAO(this);
        bookTypeAdapter = new BookTypeAdapter(typeBookList, this, this);
        rvListTypeBook.setAdapter(bookTypeAdapter);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        rvListTypeBook.setLayoutManager(manager);
    }

    private void inisViews() {
        customToolBarBook = findViewById(R.id.customtoolbarBookType);
        rvListTypeBook = findViewById(R.id.rvType);
        fabAddTypeBook = findViewById(R.id.fbtn_BookType);
        setSupportActionBar(customToolBarBook);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        customToolBarBook.setTitleTextColor(Color.WHITE);
        customToolBarBook.setTitle(getString(R.string.title_typebook));
        customToolBarBook.setNavigationIcon(R.drawable.ic_back);
    }


    private void initAction() {
        fabAddTypeBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogAddTypeBook();
            }
        });
        customToolBarBook.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        rvListTypeBook.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && fabAddTypeBook.getVisibility() == View.VISIBLE) {
                    fabAddTypeBook.hide();
                } else if (dy < 0 && fabAddTypeBook.getVisibility() != View.VISIBLE) {
                    fabAddTypeBook.show();
                }
            }
        });
    }
    private void showDialogAddTypeBook() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") View dialogView = Objects.requireNonNull(inflater).inflate(R.layout.dialog_add_book_type, null);
        builder.setView(dialogView);

        final Dialog dialog = builder.show();
        Button add = dialogView.findViewById(R.id.btnAdd_Dialog_Add_Book_Type);
        Button cancel = dialogView.findViewById(R.id.btnCancel_Dialog_Add_Book_Type);
        final EditText edTypeBookID = dialogView.findViewById(R.id.edtBookTypeID);
        final EditText edName = dialogView.findViewById(R.id.edtBookTypeName);
        final EditText edPosition = dialogView.findViewById(R.id.edtPositionType);
        final EditText edDescription = dialogView.findViewById(R.id.edtDescribe);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strId = edTypeBookID.getText().toString().trim().toUpperCase();
                String strName = edName.getText().toString().trim();
                String strPosition = edPosition.getText().toString().trim();
                String strDescription = edDescription.getText().toString().trim();

                if (strId.isEmpty() || strName.isEmpty() || strDescription.isEmpty() || strPosition.isEmpty()) {
                    if (strId.isEmpty()) {
                        edTypeBookID.setError(getString(R.string.notify_empty_IDTypeBook));
                    }
                    if (strName.isEmpty()) {
                        edName.setError(getString(R.string.notify_empty_NameTypeBook));
                    }
                    if (strDescription.isEmpty()) {
                        edDescription.setError(getString(R.string.notify_empty_Description));
                    }
                    if (strPosition.isEmpty()) {
                        edPosition.setError(getString(R.string.notify_empty_Position));
                    }
                } else if (strId.length()>5||strName.length()>50||strDescription.length()>255) {
                    if(strId.length()>5)
                        edTypeBookID.setError(getString(R.string.notify_length_typebookID));
                    if(strName.length()>50)
                        edName.setError(getString(R.string.notify_length_nameTypeBook));
                    if(strDescription.length()>255)
                        edDescription.setError(getString(R.string.notify_length_Des));
                } else {
                    BookType typeBook = bookTypeDAO.getTypeBookByID(strId);
                    if (typeBook == null) {
                        BookType typeBook1 = new BookType(strId, strName, strDescription, strPosition);
                        long result = bookTypeDAO.insertTypeBook(typeBook1);
                        if (result > 0) {
                            typeBookList = bookTypeDAO.getAllTypeBooks();
                            bookTypeAdapter.changeDataset(typeBookList);
                            dialog.dismiss();
                            Toast.makeText(BookTypeActivity.this, getString(R.string.add_successfully_typebook) + " " + strId, Toast.LENGTH_SHORT).show();
                        } else {
                            dialog.dismiss();
                            Toast.makeText(BookTypeActivity.this, getString(R.string.add_failed_typebook), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        edTypeBookID.setError(getString(R.string.notify_typebook_exists));
                    }
                }

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

    @Override
    public void onDelete(final int position) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(getString(R.string.delete_message_type_book)+" "+typeBookList.get(position).getName()+" ?");
        builder.setNegativeButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                List<Book> bookList = bookDAO.getAllBooksByIDTypeBook(typeBookList.get(position).getId());
                if (bookList.size() > 0) {
                    Toast.makeText(BookTypeActivity.this, getString(R.string.deleted_typeBook), Toast.LENGTH_SHORT).show();
                } else {
                    long result = bookTypeDAO.deleteTypeBook(typeBookList.get(position).getId());
                    if (result > 0) {
                        Toast.makeText(BookTypeActivity.this, getString(R.string.deleted_TypeBook) + " " + typeBookList.get(position).getId(), Toast.LENGTH_SHORT).show();
                        typeBookList = bookTypeDAO.getAllTypeBooks();
                        bookTypeAdapter.changeDataset(typeBookList);
                    } else {
                        Toast.makeText(BookTypeActivity.this, getString(R.string.deleted_failed), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        builder.setPositiveButton(getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    @Override
    public void onEdit(final int position) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") View dialogView = Objects.requireNonNull(inflater).inflate(R.layout.dialog_edit_type_book, null);
        builder.setView(dialogView);
        final Dialog dialog = builder.show();
        Button btnEdit = dialogView.findViewById(R.id.btnEdit_Dialog_Edit_Book_Type);
        Button btnCancel = dialogView.findViewById(R.id.btnCancel_Dialog_Edit_Book_Type);
        final EditText edName = dialogView.findViewById(R.id.edtBook_Type_Name_Dialog_Edit_Book_Type);
        final EditText edPosition = dialogView.findViewById(R.id.edtPosition_Dialog_Edit_Book_Type);
        final EditText edDescription = dialogView.findViewById(R.id.edtDescription_Dialog_Edit_Book_Type);

        String strName_old = typeBookList.get(position).getName();
        String strPosition_old = typeBookList.get(position).getPosition();
        String strDescription_old = typeBookList.get(position).getDescription();

        edName.setText(strName_old);
        edDescription.setText(strDescription_old);
        edPosition.setText(strPosition_old);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strName = edName.getText().toString().trim();
                String strPosition = edPosition.getText().toString().trim();
                String strDescription = edDescription.getText().toString().trim();

                if (strName.isEmpty() || strDescription.isEmpty() || strPosition.isEmpty()) {

                    if (strName.isEmpty()) {
                        edName.setError(getString(R.string.notify_empty_NameTypeBook));
                    }
                    if (strDescription.isEmpty()) {
                        edDescription.setError(getString(R.string.notify_empty_Description));
                    }
                    if (strPosition.isEmpty()) {
                        edPosition.setError(getString(R.string.notify_empty_Position));
                    }
                } else if (strName.length()>50||strDescription.length()>255) {
                    if(strName.length()>50)
                        edName.setError(getString(R.string.notify_length_nameTypeBook));
                    if(strDescription.length()>255)
                        edDescription.setError(getString(R.string.notify_length_Des));
                } else {
                    BookType typeBook = new BookType(typeBookList.get(position).getId(), strName, strDescription, strPosition);
                    long result = bookTypeDAO.updateTypeBook(typeBook);
                    if (result > 0) {
                        Toast.makeText(BookTypeActivity.this, getString(R.string.editedTypeBook) + " " + typeBookList.get(position).getId(), Toast.LENGTH_SHORT).show();
                        typeBookList = bookTypeDAO.getAllTypeBooks();
                        bookTypeAdapter.changeDataset(typeBookList);
                        dialog.dismiss();
                    } else {
                        Toast.makeText(BookTypeActivity.this, getString(R.string.edited_faied), Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}