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
import com.nho_pc.nhopvph06243_ass.dao.BookDAO;
import com.nho_pc.nhopvph06243_ass.database.DatabaseHelper;
import com.nho_pc.nhopvph06243_ass.model.Book;
import com.nho_pc.nhopvph06243_ass.ui.BookActivity;

import java.util.List;

public class BookAdapter extends BaseAdapter {
    private List<Book> bookList;
    public Activity context;
    public LayoutInflater inflater;
    private BookDAO bookDAO;
    private DatabaseHelper databaseHelper;

    public BookAdapter(Activity context, List<Book> arrayBook) {
        super();
        this.context = context;
        this.bookList = arrayBook;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        databaseHelper = new DatabaseHelper(context);
        bookDAO = new BookDAO(context);
    }

    @Override
    public int getCount() {
        if (bookList == null) {
            return 0;
        }
        return bookList.size();
    }

    @Override
    public Object getItem(int position) {
        return bookList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public static class ViewHolder {
        ImageView img;
        TextView txtBookName;
        TextView txtSoLuong;
        ImageView imgDelete;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        BookAdapter.ViewHolder holder;
        if (convertView == null) {
            holder = new BookAdapter.ViewHolder();
            convertView = inflater.inflate(R.layout.item_book, null);
            holder.img = (ImageView) convertView.findViewById(R.id.imgAvatarBook);
            holder.txtBookName = (TextView) convertView.findViewById(R.id.tvBookName);
            holder.txtSoLuong = (TextView) convertView.findViewById(R.id.tvSoLuongBook);
            holder.imgDelete = (ImageView) convertView.findViewById(R.id.imgDeleteBook);
            holder.imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //B1: định nghĩa alertDialog
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    //B2: thiết lập thông tin
                    builder.setTitle("Thông báo");
                    builder.setMessage("Bạn có muốn xóa " + bookList.get(position).getTenSach() + " ?");
                    builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            bookDAO.deleteBookByID(bookList.get(position).getMaSach());
                            bookList.remove(position);
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
            holder = (BookAdapter.ViewHolder) convertView.getTag();
        Book _entry = (Book) bookList.get(position);
        holder.img.setImageResource(R.drawable.ic_avatar_user);
        holder.txtBookName.setText(_entry.getTenSach());
        holder.txtSoLuong.setText(_entry.getSoLuong());
        return convertView;
    }
    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
    public void changeDataset(List<Book> items){
        this.bookList = items;
        notifyDataSetChanged();
    }
}
