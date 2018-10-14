package com.nho_pc.nhopvph06243_ass.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nho_pc.nhopvph06243_ass.R;
import com.nho_pc.nhopvph06243_ass.model.Book;

import java.util.List;

public class BookSpinnerAdapter extends BaseAdapter {


    public Context context;
    private List<Book> books;

    public BookSpinnerAdapter(Context context, List<Book> books) {
        this.context = context;
        this.books = books;
    }

    @Override
    public int getCount() {
        return books.size();
    }

    @Override
    public Object getItem(int i) {
        return books.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }


    @SuppressLint({"SetTextI18n", "ViewHolder"})
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.type_book_spinner, viewGroup, false);
        TextView tvName;
        tvName = view.findViewById(R.id.tvName);
        tvName.setText(i + "|" + books.get(i).getBookName());
        return view;
    }
}
