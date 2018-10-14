package com.nho_pc.nhopvph06243_ass.adapter;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nho_pc.nhopvph06243_ass.R;
import com.nho_pc.nhopvph06243_ass.listener.OnDelete;
import com.nho_pc.nhopvph06243_ass.listener.OnEdit;
import com.nho_pc.nhopvph06243_ass.model.BookType;

import java.util.List;

public class BookTypeAdapter extends RecyclerView.Adapter<BookTypeAdapter.ViewHolder> {
    private List<BookType> typeBookList;
    private final OnDelete onDelete;
    private final OnEdit onEdit;



    public BookTypeAdapter(List<BookType> typeBookList, OnDelete onDelete, OnEdit onEdit) {
        this.typeBookList = typeBookList;
        this.onDelete = onDelete;
        this.onEdit = onEdit;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_type, parent, false);
        return new ViewHolder(itemView);
    }

    @SuppressLint({"RecyclerView", "SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        BookType typeBook = typeBookList.get(position);
        if (typeBook.getName().length() > 20) {
            String name = "Tên : " + typeBook.getName().substring(0, 20) + "...";
            holder.tvName.setText(name);
        } else {
            holder.tvName.setText("Tên: " + typeBook.getName());
        }
        if (typeBook.getId().length() > 20) {
            String id = "Mã : " + typeBook.getId().substring(0, 20) + "...";
            holder.tvTypeBook_ID.setText(id);
        } else {
            holder.tvTypeBook_ID.setText("Mã: " + typeBook.getId());
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
        if (typeBookList == null) return 0;
        return typeBookList.size();
    }

    public void changeDataset(List<BookType> items) {
        this.typeBookList = items;
        notifyDataSetChanged();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView imgEdit;
        final ImageView imgDelete;
        final TextView tvName;
        final TextView tvTypeBook_ID;

        ViewHolder(View itemView) {
            super(itemView);
            tvTypeBook_ID = itemView.findViewById(R.id.tvType_ID_Item_Type);
            tvName = itemView.findViewById(R.id.tvName_Book_Type_Item_Type);
            imgEdit = itemView.findViewById(R.id.imgEdit_Book_Type_Item_Type);
            imgDelete = itemView.findViewById(R.id.imgDelete_Book_Type_Item_Type);
        }

    }

//    public void resetData() {
//        typeBookList = typeBookSortList;
//    }
//
//    public Filter getFilter() {
//        if (bookTypeFilter == null)
//            bookTypeFilter = new CustomFilter();
//        return bookTypeFilter;
//    }
//
//    private class CustomFilter extends Filter {
//        @Override
//        protected FilterResults performFiltering(CharSequence constraint) {
//            FilterResults results = new FilterResults();
//            // We implement here the filter logic
//            if (constraint == null || constraint.length() == 0) {
//                results.values = typeBookSortList;
//                results.count = typeBookSortList.size();
//            } else {
//                List<BookType> bookTypeList = new ArrayList<BookType>();
//                for (BookType p : typeBookList) {
//                    if
//                            (p.getId().toUpperCase().startsWith(constraint.toString().toUpperCase()))
//                        bookTypeList.add(p);
//                }
//                results.values = bookTypeList;
//                results.count = bookTypeList.size();
//            }
//            return results;
//        }
//
//        @Override
//        protected void publishResults(CharSequence constraint, FilterResults results) {
//            if (results.count == 0)
//                recyclerView.invalidate();
//            else {
//                typeBookList = (List<BookType>)results.values;
//                notifyDataSetChanged();
//            }
//        }
//    }
}