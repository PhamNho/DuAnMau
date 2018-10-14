package com.nho_pc.nhopvph06243_ass.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nho_pc.nhopvph06243_ass.R;
import com.nho_pc.nhopvph06243_ass.model.BookType;

import java.util.List;

public class TypeBookSpinnerAdapter extends BaseAdapter {


    public Context context;
    private List<BookType> bookTypeList;

    public TypeBookSpinnerAdapter(Context context, List<BookType> bookTypeList) {
        this.context = context;
        this.bookTypeList = bookTypeList;
    }

    @Override
    public int getCount() {
        return bookTypeList.size();
    }

    @Override
    public Object getItem(int i) {
        return bookTypeList.get(i);
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
        tvName.setText(i + "  |  " + bookTypeList.get(i).getName());
        return view;
    }

}
