package com.nho_pc.nhopvph06243_ass.ui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import com.nho_pc.nhopvph06243_ass.R;
import com.nho_pc.nhopvph06243_ass.adapter.BookAdapter;
import com.nho_pc.nhopvph06243_ass.dao.BillDetailDAO;
import com.nho_pc.nhopvph06243_ass.dao.BookDAO;
import com.nho_pc.nhopvph06243_ass.dao.BookTypeDAO;
import com.nho_pc.nhopvph06243_ass.listener.Constant;

import com.nho_pc.nhopvph06243_ass.listener.OnDelete;
import com.nho_pc.nhopvph06243_ass.listener.OnEdit;
import com.nho_pc.nhopvph06243_ass.model.BillDetail;
import com.nho_pc.nhopvph06243_ass.model.Book;
import com.nho_pc.nhopvph06243_ass.model.BookType;

import java.util.List;
import java.util.Objects;

public class BookActivity extends AppCompatActivity implements OnEdit, OnDelete {
    private Toolbar customToolbarBook;
    private FloatingActionButton fabAddBook;
    private RecyclerView rvListBook;
    private List<Book> bookList;
    private BookAdapter bookAdapter;
    private BookDAO bookDAO;
    private BookTypeDAO typeBookDAO;
    private String TypeBookID = null;
    private BillDetailDAO billDetailDAO;
    private List<BillDetail> billDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        initViews();
        initAction();
        typeBookDAO = new BookTypeDAO(this);
        bookDAO = new BookDAO(this);
        billDetailDAO = new BillDetailDAO(this);
        bookList = bookDAO.getAllBooks();
        bookAdapter = new BookAdapter(bookList, this, this);
        rvListBook.setAdapter(bookAdapter);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        rvListBook.setLayoutManager(manager);
    }

    private void initAction() {
        fabAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogAddBook();
            }
        });
        customToolbarBook.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        rvListBook.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && fabAddBook.getVisibility() == View.VISIBLE) {
                    fabAddBook.hide();
                } else if (dy < 0 && fabAddBook.getVisibility() != View.VISIBLE) {
                    fabAddBook.show();
                }
            }
        });
    }

    private void initViews() {
        fabAddBook = findViewById(R.id.fbtn_Book);
        customToolbarBook = findViewById(R.id.customtoolbarBook);
        rvListBook = findViewById(R.id.rvBook);
        setSupportActionBar(customToolbarBook);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        customToolbarBook.setTitleTextColor(Color.WHITE);
        customToolbarBook.setTitle(getString(R.string.title_book));
        customToolbarBook.setNavigationIcon(R.drawable.ic_back);
    }

    private void showDialogAddBook() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") View dialogView = Objects.requireNonNull(inflater).inflate(R.layout.dialog_add_book, null);
        builder.setView(dialogView);
        final Dialog dialog = builder.show();

        final EditText edBookID = dialogView.findViewById(R.id.edtBookCode);
        final EditText edBookName = dialogView.findViewById(R.id.edtBookName);
        final Spinner spTypeBookID = dialogView.findViewById(R.id.spnBookType);
        final EditText edAuthor = dialogView.findViewById(R.id.edtAuthorName);
        final EditText edNXB = dialogView.findViewById(R.id.edtPublisher);
        final EditText edPrice = dialogView.findViewById(R.id.edtPriceBook);
        final EditText edQuantity = dialogView.findViewById(R.id.edtQuantily);
        Button btnAdd = dialogView.findViewById(R.id.btnAdd_Dialog_Add_Book);
        Button btnCancel = dialogView.findViewById(R.id.btnCancel_Dialog_Add_Book);
        ImageView imgAddTypeBook = dialogView.findViewById(R.id.imgAdd_Book_Type);
        imgAddTypeBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                showDialogAddTypeBook();
            }
        });

        Cursor cursorTypeBook = typeBookDAO.getTypeBook();
        SimpleCursorAdapter adapterTypeBook;
        if (cursorTypeBook != null) {
            //noinspection deprecation
            adapterTypeBook = new SimpleCursorAdapter(BookActivity.this,
                    R.layout.item_spiner_type_book,
                    cursorTypeBook,
                    new String[]{ Constant.TB_COLUMN_TYPE_NAME},
                    new int[]{ R.id.tvSpinerTenTheLoai});
            spTypeBookID.setAdapter(adapterTypeBook);
        }
        spTypeBookID.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Cursor c = (Cursor) parent.getItemAtPosition(position);
                TypeBookID = c.getString(c.getColumnIndex(Constant.TB_COLUMN_TYPE_BOOK_ID));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strBookID = edBookID.getText().toString().trim().toUpperCase();
                String strBookName = edBookName.getText().toString().trim();
                String strAuthor = edAuthor.getText().toString().trim();
                String strNXB = edNXB.getText().toString().trim();
                String strPrice = edPrice.getText().toString().trim();
                String strQuantity = edQuantity.getText().toString().trim();

                if (TypeBookID == null) {
                    Toast.makeText(BookActivity.this, getString(R.string.add_typeBook), Toast.LENGTH_SHORT).show();
                } else {
                    if (strBookID.isEmpty() || strBookName.isEmpty() || strAuthor.isEmpty() || strNXB.isEmpty() || strPrice.isEmpty() || strQuantity.isEmpty()) {
                        if (strBookID.isEmpty())
                            edBookID.setError(getString(R.string.notify_empty_BookID));
                        if (strBookName.isEmpty())
                            edBookName.setError(getString(R.string.notify_empty_BookName));
                        if (strAuthor.isEmpty())
                            edAuthor.setError(getString(R.string.notify_empty_BookAuthor));
                        if (strNXB.isEmpty())
                            edNXB.setError(getString(R.string.notify_empty_NXB));
                        if (strPrice.isEmpty())
                            edPrice.setError(getString(R.string.notify_empty_BookPrice));
                        if (strQuantity.isEmpty())
                            edQuantity.setError(getString(R.string.notify_empty_Quantity));
                    } else if (strBookID.length() > 5 || strAuthor.length() > 50 || strNXB.length() > 50 || strBookName.length() > 50) {
                        if (strBookID.length() > 5)
                            edBookID.setError(getString(R.string.notify_length_BookID));
                        if (strAuthor.length() > 50)
                            edAuthor.setError(getString(R.string.notify_length_Author));
                        if (strNXB.length() > 50)
                            edNXB.setError(getString(R.string.notify_length_NXB));
                        if (strBookName.length() > 50)
                            edBookName.setError(getString(R.string.notify_length_BookName));
                    } else {

                        Book book = bookDAO.getBookByID(strBookID);
                        if (book == null) {
                            Book book1 = new Book(strBookID, strBookName, TypeBookID, strAuthor, strNXB, strPrice, strQuantity);
                            long result = bookDAO.insertBook(book1);
                            if (result > 0) {
                                bookList = bookDAO.getAllBooks();
                                bookAdapter.changeDataset(bookList);
                                Toast.makeText(BookActivity.this, getString(R.string.add_successfully_book) + " " + strBookID, Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            } else {
                                Toast.makeText(BookActivity.this, getString(R.string.add_failed_book) + " " + strBookID, Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        } else {
                            edBookID.setError(getString(R.string.notify_BookID_exists));
                        }

                    }
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    @Override
    public void onDelete(final int position) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(getString(R.string.delete_message_book)+" "+bookList.get(position).getBookName() +" ?");
        builder.setNegativeButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                List<BillDetail> billDetail = billDetailDAO.getAllBillDetailsByBookID(bookList.get(position).getBookID());
                Log.e("abc", billDetail.size() + "");
                if (billDetail.size() > 0) {
                    Toast.makeText(BookActivity.this, getString(R.string.delete_book_BillDetail), Toast.LENGTH_SHORT).show();
                } else {
                    long result = bookDAO.deleteBook(bookList.get(position).getBookID());
                    if (result > 0) {
                        Toast.makeText(BookActivity.this, getString(R.string.deleted_book) + " " + bookList.get(position).getBookName(), Toast.LENGTH_SHORT).show();
                        bookList = bookDAO.getAllBooks();
                        bookAdapter.changeDataset(bookList);
                    } else {
                        Toast.makeText(BookActivity.this, getString(R.string.deleted_failed), Toast.LENGTH_SHORT).show();
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
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") View dialogView = Objects.requireNonNull(inflater).inflate(R.layout.dialog_edit_book, null);
        builder.setView(dialogView);
        final Dialog dialog = builder.show();

        final EditText edBookName = dialogView.findViewById(R.id.edtBook_Name_Dialog_Edit_Book);
        final Spinner spTypeBookID = dialogView.findViewById(R.id.spnType_Book_ID_Dialog_Edit_Book);
        final EditText edAuthor = dialogView.findViewById(R.id.edtAuthor_Dialog_Edit_Book);
        final EditText edNXB = dialogView.findViewById(R.id.edtNXB_Dialog_Edit_Book);
        final EditText edPrice = dialogView.findViewById(R.id.edtPrice_Dialog_Edit_Book);
        final EditText edQuantity = dialogView.findViewById(R.id.edtQuantity_Dialog_Edit_Book);
        Button btnEdit = dialogView.findViewById(R.id.btnEdit_Dialog_Edit_Book);

        billDetails = billDetailDAO.getAllBillDetailsByBookID(bookList.get(position).getBookID());
        Log.e("getQuantity", getAllQuantity() + "");
        String strBookName_Old = bookList.get(position).getBookName();
        String strAuthor_Old = bookList.get(position).getAuthor();
        String strNXB_Old = bookList.get(position).getNXB();
        String strPrice_Old = bookList.get(position).getPrice();
        String strQuantity_Old = bookList.get(position).getQuantity();
        String strtypebookID = bookList.get(position).getType_Book_ID();
        Log.e("strtypebookID", strtypebookID);
        edBookName.setText(strBookName_Old);
        edAuthor.setText(strAuthor_Old);
        edNXB.setText(strNXB_Old);
        edPrice.setText(strPrice_Old);
        edQuantity.setText(strQuantity_Old);

        Cursor cursorTypeBook = typeBookDAO.getTypeBook();
        SimpleCursorAdapter adapterTypeBook;
        if (cursorTypeBook != null) {
            //noinspection deprecation
            adapterTypeBook = new SimpleCursorAdapter(BookActivity.this,
                    R.layout.item_spiner_type_book,
                    cursorTypeBook,
                    new String[]{Constant.TB_COLUMN_TYPE_NAME},
                    new int[]{R.id.tvSpinerTenTheLoai});
            spTypeBookID.setAdapter(adapterTypeBook);
        }
        spTypeBookID.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Cursor c = (Cursor) parent.getItemAtPosition(position);
                TypeBookID = c.getString(c.getColumnIndex(Constant.TB_COLUMN_TYPE_BOOK_ID));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strBookName_New = edBookName.getText().toString().trim();
                String strAuthor_New = edAuthor.getText().toString().trim();
                String strNXB_New = edNXB.getText().toString().trim();
                String strPrice_New = edPrice.getText().toString().trim();
                String strQuantity_New = edQuantity.getText().toString().trim();
                if (TypeBookID == null) {
                    Toast.makeText(BookActivity.this, getString(R.string.add_typeBook), Toast.LENGTH_SHORT).show();
                } else {
                    if (strBookName_New.isEmpty() || strAuthor_New.isEmpty() || strNXB_New.isEmpty() || strPrice_New.isEmpty() || strQuantity_New.isEmpty()) {
                        if (strBookName_New.isEmpty())
                            edBookName.setError(getString(R.string.notify_empty_BookName));
                        if (strAuthor_New.isEmpty())
                            edAuthor.setError(getString(R.string.notify_empty_BookAuthor));
                        if (strNXB_New.isEmpty())
                            edNXB.setError(getString(R.string.notify_empty_NXB));
                        if (strPrice_New.isEmpty())
                            edPrice.setError(getString(R.string.notify_empty_BookPrice));
                        if (strQuantity_New.isEmpty())
                            edQuantity.setError(getString(R.string.notify_empty_Quantity));
                    } else if (strAuthor_New.length() > 50 || strNXB_New.length() > 50 || strBookName_New.length() > 50) {
                        if (strAuthor_New.length() > 50)
                            edAuthor.setError(getString(R.string.notify_length_Author));
                        if (strNXB_New.length() > 50)
                            edNXB.setError(getString(R.string.notify_length_NXB));
                        if (strBookName_New.length() > 50)
                            edBookName.setError(getString(R.string.notify_length_BookName));
                    } else if (Integer.parseInt(strQuantity_New) < getAllQuantity()) {
                        edQuantity.setError(getString(R.string.notify_Quantity_Old));
                    } else {
                        Book book = new Book();
                        book.setBookID(bookList.get(position).getBookID());
                        book.setType_Book_ID(TypeBookID);
                        book.setQuantity(strQuantity_New);
                        book.setPrice(strPrice_New);
                        book.setNXB(strNXB_New);
                        book.setAuthor(strAuthor_New);
                        book.setBookName(strBookName_New);
                        long result = bookDAO.updateBook(book);
                        if (result > 0) {
                            Toast.makeText(BookActivity.this, getString(R.string.editedBook) + " " + bookList.get(position).getBookID(), Toast.LENGTH_SHORT).show();
                            bookList = bookDAO.getAllBooks();
                            bookAdapter.changeDataset(bookList);
                            dialog.dismiss();
                        } else {
                            Toast.makeText(BookActivity.this, getString(R.string.edited_faied), Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    }
                }
            }
        });
        Button btnCancel = dialogView.findViewById(R.id.btnCancel_Dialog_Edit_Book);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private void showDialogAddTypeBook() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") View dialogView = Objects.requireNonNull(inflater).inflate(R.layout.dialog_add_book_type, null);
        dialog.setView(dialogView);
        final Dialog dialog1 = dialog.show();
        Button btnAdd = dialogView.findViewById(R.id.btnAdd_Dialog_Add_Book_Type);
        Button btnCancel = dialogView.findViewById(R.id.btnCancel_Dialog_Add_Book_Type);
        final EditText edId = dialogView.findViewById(R.id.edtBookTypeID);
        final EditText edName = dialogView.findViewById(R.id.edtBookTypeName);
        final EditText edPosition = dialogView.findViewById(R.id.edtPositionType);
        final EditText edDescription = dialogView.findViewById(R.id.edtDescribe);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strId = edId.getText().toString().trim().toUpperCase();
                String strName = edName.getText().toString().trim();
                String strPosition = edPosition.getText().toString().trim();
                String strDescription = edDescription.getText().toString().trim();

                if (strId.isEmpty() || strName.isEmpty() || strDescription.isEmpty() || strPosition.isEmpty()) {
                    if (strId.isEmpty()) {
                        edId.setError(getString(R.string.notify_empty_IDTypeBook));
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
                } else if (strId.length() > 5 || strName.length() > 50 || strDescription.length() > 255) {
                    if (strId.length() > 5)
                        edId.setError(getString(R.string.notify_length_typebookID));
                    if (strName.length() > 50)
                        edName.setError(getString(R.string.notify_length_nameTypeBook));
                    if (strDescription.length() > 255)
                        edDescription.setError(getString(R.string.notify_length_Des));
                } else {
                    BookType typeBook = typeBookDAO.getTypeBookByID(strId);
                    if (typeBook == null) {
                        BookType typeBook1 = new BookType(strId, strName, strDescription, strPosition);
                        long result = typeBookDAO.insertTypeBook(typeBook1);
                        if (result > 0) {
                            Toast.makeText(BookActivity.this, getString(R.string.add_successfully_typebook) + " " + strId, Toast.LENGTH_SHORT).show();
                            dialog1.dismiss();
                            showDialogAddBook();

                        } else {
                            dialog1.dismiss();
                            Toast.makeText(BookActivity.this, getString(R.string.add_failed_typebook), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        edId.setError(getString(R.string.notify_typebook_exists));
                    }
                }

            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });
    }

    private int getAllQuantity() {
        int quantity = 0;
        for (int i = 0; i < billDetails.size(); i++) {
            quantity = quantity + Integer.parseInt(billDetails.get(i).getQuantity());
        }
        return quantity;
    }
}
