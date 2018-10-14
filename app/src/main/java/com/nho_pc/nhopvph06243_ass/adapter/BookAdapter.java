package com.nho_pc.nhopvph06243_ass.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.nho_pc.nhopvph06243_ass.R;
import com.nho_pc.nhopvph06243_ass.dao.BookDAO;
import com.nho_pc.nhopvph06243_ass.database.DatabaseHelper;
import com.nho_pc.nhopvph06243_ass.listener.OnDelete;
import com.nho_pc.nhopvph06243_ass.listener.OnEdit;
import com.nho_pc.nhopvph06243_ass.model.Book;
import com.nho_pc.nhopvph06243_ass.ui.BookActivity;

import java.util.ArrayList;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder>{
    private List<Book> bookList;
    private final OnDelete onDelete;
    private final OnEdit onEdit;

    public BookAdapter(List<Book> bookList, OnDelete onDelete, OnEdit onEdit) {
        this.bookList = bookList;
        this.onDelete = onDelete;
        this.onEdit = onEdit;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_book,parent,false);
        return new ViewHolder(itemView);
    }

    @SuppressLint({"RecyclerView", "SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Book book = bookList.get(position);
        if(book.getBookName().length()>20){
            String name = "Tên: "+book.getBookName().substring(0,20)+"...";
            holder.tvName.setText(name);
        }else{
            holder.tvName.setText("Tên: "+book.getBookName());
        }
        if(book.getQuantity().length()>5){
            String name =book.getQuantity().substring(0,5)+"...";
            holder.tvQuantity.setText(name);
        }else{
            holder.tvQuantity.setText(book.getQuantity());
        }
        if(book.getBookID().length()>12){
            String name = book.getBookID().substring(0,12)+"...";
            holder.tvBooK_ID.setText(name);
        }else{
            holder.tvBooK_ID.setText(book.getBookID());
        }

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDelete.onDelete(position);
            }
        });
        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEdit.onEdit(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (bookList == null) return 0;
        return bookList.size();
    }
    public void changeDataset(List<Book> items){
        this.bookList = items;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView imgEdit;
        final ImageView imgDelete;
        final TextView tvName;
        final TextView tvQuantity;
        final TextView tvBooK_ID;

        ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvBook_Name_Item_Book);
            tvQuantity = itemView.findViewById(R.id.tvQuality_Item_Book);
            tvBooK_ID =itemView.findViewById(R.id.tvBook_ID_Item_Book);
            imgEdit = itemView.findViewById(R.id.imgEdit_Item_Book);
            imgDelete = itemView.findViewById(R.id.imgDelete_Item_Book);
        }

    }
}
