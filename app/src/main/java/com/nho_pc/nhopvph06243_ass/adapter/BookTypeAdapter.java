package com.nho_pc.nhopvph06243_ass.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nho_pc.nhopvph06243_ass.R;
import com.nho_pc.nhopvph06243_ass.dao.BookTypeDAO;
import com.nho_pc.nhopvph06243_ass.database.DatabaseHelper;
import com.nho_pc.nhopvph06243_ass.model.BookType;
import com.nho_pc.nhopvph06243_ass.model.Users;

import java.util.List;

public class BookTypeAdapter extends BaseAdapter {
    private List<BookType> bookTypeList;
    public Activity context;
    public LayoutInflater inflater;
    private BookTypeDAO bookTypeDAO;
    private DatabaseHelper databaseHelper;

    public BookTypeAdapter(Activity context, List<BookType> arrayBookType) {
        super();
        this.context = context;
        this.bookTypeList = arrayBookType;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        databaseHelper=new DatabaseHelper(context);
        bookTypeDAO = new BookTypeDAO(context);
    }

    @Override
    public int getCount() {
        if (bookTypeList == null) {
            return 0;
        }
        return bookTypeList.size();
    }

    @Override
    public Object getItem(int position) {
        return bookTypeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    public static class ViewHolder {
        ImageView img;
        TextView txtTypeID;
        TextView txtTypeName;
        ImageView imgDelete;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_type, null);
            holder.img = (ImageView) convertView.findViewById(R.id.imgAvatarType);
            holder.txtTypeID = (TextView) convertView.findViewById(R.id.tvTypeID);
            holder.txtTypeName = (TextView) convertView.findViewById(R.id.tvTypeName);
            holder.imgDelete = (ImageView) convertView.findViewById(R.id.imgDeleteType);
            holder.imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //B1: định nghĩa alertDialog
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    //B2: thiết lập thông tin
                    builder.setTitle("Thông báo");
                    builder.setMessage("Bạn có muốn xóa " + bookTypeList.get(position).getTypeName() + " ?");
                    builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            bookTypeDAO.deleteBookTypeByID(bookTypeList.get(position).getTypeID());
                            bookTypeList.remove(position);
                            notifyDataSetChanged();
                        }
                    });
                    builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    //B3: hiển thị
                    builder.show();
                }
            });
            convertView.setTag(holder);
        } else
            holder = (BookTypeAdapter.ViewHolder) convertView.getTag();
            BookType _entry = (BookType) bookTypeList.get(position);
            holder.img.setImageResource(R.drawable.ic_avatar_user);
            holder.txtTypeID.setText(_entry.getTypeID());
            holder.txtTypeName.setText(_entry.getTypeName());
            return convertView;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    public void changeDataset(List<BookType> items) {
        this.bookTypeList = items;
        notifyDataSetChanged();
    }
}