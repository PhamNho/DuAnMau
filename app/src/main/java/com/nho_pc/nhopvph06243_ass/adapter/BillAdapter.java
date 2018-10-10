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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nho_pc.nhopvph06243_ass.R;
import com.nho_pc.nhopvph06243_ass.dao.BillDAO;
import com.nho_pc.nhopvph06243_ass.dao.BillDetailDAO;
import com.nho_pc.nhopvph06243_ass.dao.BookDAO;
import com.nho_pc.nhopvph06243_ass.database.DatabaseHelper;
import com.nho_pc.nhopvph06243_ass.model.Bill;
import com.nho_pc.nhopvph06243_ass.model.Book;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class BillAdapter extends BaseAdapter {
    List<Bill> arrBill;
    List<Bill> arrSortBill;
    private Filter billFilter;
    public Activity context;
    public LayoutInflater inflater;
    BillDAO billDAO;
    BillDetailDAO billDetailDAO;
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

    public BillAdapter(Activity context, List<Bill> arrayBill) {
        super();
        this.context = context;
        this.arrBill = arrayBill;
        this.arrSortBill = arrayBill;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        billDAO = new BillDAO(context);
        billDetailDAO = new BillDetailDAO(context);
    }

    @Override
    public int getCount() {
        return arrBill.size();
    }

    @Override
    public Object getItem(int position) {
        return arrBill.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public static class ViewHolder {
        ImageView img;
        TextView txtMaHoaDon;
        TextView txtNgayMua;
        ImageView imgDelete;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_bill, null);
            holder.img = (ImageView) convertView.findViewById(R.id.imgAvatarBill);
            holder.txtMaHoaDon = (TextView) convertView.findViewById(R.id.tvBillID);
            holder.txtNgayMua = (TextView) convertView.findViewById(R.id.tvBillDate);
            holder.imgDelete = (ImageView) convertView.findViewById(R.id.imgDeleteBill);
            holder.imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //B1: định nghĩa alertDialog
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    //B2: thiết lập thông tin
                    builder.setTitle("Thông báo");
                    builder.setMessage("Bạn có muốn xóa " + arrBill.get(position).getMaHoaDon() + " ?");
                    builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            billDAO.deleteBillByID(arrBill.get(position).getMaHoaDon());
                            arrBill.remove(position);
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
        Bill _entry = (Bill) arrBill.get(position);
        holder.img.setImageResource(R.drawable.ic_bill);
        holder.txtMaHoaDon.setText(_entry.getMaHoaDon());
        holder.txtNgayMua.setText(sdf.format(_entry.getNgayMua()));
        return convertView;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    public void changeDataset(List<Bill> items) {
        this.arrBill = items;
        notifyDataSetChanged();
    }

    public void resetData() {
        arrBill = arrSortBill;
    }

    public Filter getFilter() {
        if (billFilter == null)
            billFilter = new CustomFilter();
        return billFilter;
    }

    private class CustomFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint == null || constraint.length() == 0) {
                results.values = arrSortBill;
                results.count = arrSortBill.size();
            } else {
                List<Bill> lsHoaDon = new ArrayList<Bill>();
                for (Bill p : arrBill) {
                    if (p.getMaHoaDon().toUpperCase().startsWith(constraint.toString().toUpperCase()))
                        lsHoaDon.add(p);
                }
                results.values = lsHoaDon;
                results.count = lsHoaDon.size();
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
// Now we have to inform the adapter about the new list filtered
            if (results.count == 0)
                notifyDataSetInvalidated();
            else {
                arrBill = (List<Bill>) results.values;
                notifyDataSetChanged();
            }
        }
    }
}