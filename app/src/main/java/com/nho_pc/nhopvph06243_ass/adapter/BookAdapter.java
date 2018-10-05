package com.nho_pc.nhopvph06243_ass.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.nho_pc.nhopvph06243_ass.model.Book;
import com.nho_pc.nhopvph06243_ass.ui.BookActivity;

import java.util.ArrayList;
import java.util.List;

public class BookAdapter extends BaseAdapter implements Filterable {
    List<Book> arrBook;
    List<Book> arrSortBook;
    private Filter bookFilter;
    public Activity context;
    public LayoutInflater inflater;
    BookDAO bookDAO;
    public BookAdapter(Activity context, List<Book> arrayBook) {
        super();
        this.context = context;
        this.arrBook = arrayBook;
        this.arrSortBook = arrayBook;
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        bookDAO = new BookDAO(context);
    }
    @Override
    public int getCount() {
        return arrBook.size();
    }
    @Override
    public Object getItem(int position) {
        return arrBook.get(position);
    }
    @Override
    public long getItemId(int position) {
        return 0;
    }
    public static class ViewHolder {
        ImageView img;
        TextView txtBookName;
        TextView txtBookPrice;
        TextView txtSoLuong;
        ImageView imgDelete;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null)
        {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_book, null);
            holder.img = (ImageView) convertView.findViewById(R.id.imgAvatarBook);
            holder.txtBookName = (TextView) convertView.findViewById(R.id.tvBookName);
            holder.txtSoLuong = (TextView) convertView.findViewById(R.id.tvSoLuongBook);
            holder.txtBookPrice=convertView.findViewById(R.id.tvBookPrice);
            holder.imgDelete = (ImageView) convertView.findViewById(R.id.imgDeleteBook);
            holder.imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //B1: định nghĩa alertDialog
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    //B2: thiết lập thông tin
                    builder.setTitle("Thông báo");
                    builder.setMessage("Bạn có muốn xóa " + arrBook.get(position).getTenSach() + " ?");
                    builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            bookDAO.deleteBookByID(arrBook.get(position).getMaSach());
                            arrBook.remove(position);
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
            holder = (ViewHolder) convertView.getTag();
        Book _entry = (Book) arrBook.get(position);
        holder.img.setImageResource(R.drawable.ic_book);
        holder.txtBookName.setText("Mã sách: "+_entry.getMaSach());
        holder.txtSoLuong.setText("Số lượng: "+_entry.getSoLuong());
        holder.txtBookPrice.setText("Giá: "+ _entry.getGiaBia());
        return convertView;
    }
    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
    public void changeDataset(List<Book> items){
        this.arrBook = items;
        notifyDataSetChanged();
    }
    public void resetData() {
        arrBook = arrSortBook;
    }
    public Filter getFilter() {
        if (bookFilter == null)
            bookFilter = new CustomFilter();
        return bookFilter;
    }
    private class CustomFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
// We implement here the filter logic
            if (constraint == null || constraint.length() == 0) {
                results.values = arrSortBook;
                results.count = arrSortBook.size();
            }
            else {
                List<Book> bookList = new ArrayList<Book>();
                for (Book p : arrBook) {
                    if (p.getMaSach().toUpperCase().startsWith(constraint.toString().toUpperCase()))
                        bookList.add(p);
                }
                results.values = bookList;
                results.count = bookList.size();
            }
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            if (results.count == 0)
                notifyDataSetInvalidated();
            else {
                arrBook = (List<Book>) results.values;
                notifyDataSetChanged();
            }
        }
    }
}