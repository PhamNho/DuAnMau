package com.nho_pc.nhopvph06243_ass.adapter;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nho_pc.nhopvph06243_ass.R;
import com.nho_pc.nhopvph06243_ass.dao.BookDAO;
import com.nho_pc.nhopvph06243_ass.model.Book;

import java.util.List;

public class Top10BookAdapter  extends RecyclerView.Adapter<Top10BookAdapter.ViewHolder>{
    private final List<Book> bookList;
    private BookDAO bookDAO;

    public Top10BookAdapter(List<Book> bookList) {
        this.bookList = bookList;
    }

    @NonNull
    @Override
    public Top10BookAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        bookDAO = new BookDAO(parent.getContext());
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_top10_book,parent,false);
        return new ViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull Top10BookAdapter.ViewHolder holder, int position) {
        Book book = bookList.get(position);
        Book book1=bookDAO.getBookByID(book.getBookID());
        holder.tvName.setText("Tên sách: "+book1.getBookName());
        holder.tvQuantity.setText("Số lượng: "+book.getQuantity());
        holder.tvBook_ID.setText("Mã sách: "+book.getBookID());
    }

    @Override
    public int getItemCount() {
        if (bookList == null) return 0;
        return bookList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final TextView tvName;
        final TextView tvQuantity;
        final TextView tvBook_ID;

        ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvBook_Name_Item_Top10_Book);
            tvQuantity = itemView.findViewById(R.id.tvQuantity_Item_Top10_Book);
            tvBook_ID =itemView.findViewById(R.id.tvBook_ID_Item_Top10_Book);
        }

    }
}
