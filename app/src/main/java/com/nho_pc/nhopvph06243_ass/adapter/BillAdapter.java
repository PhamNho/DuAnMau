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
import com.nho_pc.nhopvph06243_ass.dao.BillDAO;
import com.nho_pc.nhopvph06243_ass.dao.BookDAO;
import com.nho_pc.nhopvph06243_ass.database.DatabaseHelper;
import com.nho_pc.nhopvph06243_ass.model.Bill;
import com.nho_pc.nhopvph06243_ass.model.Book;

import java.util.List;

public class BillAdapter extends BaseAdapter {
    private List<Bill> billList;
    public Activity context;
    public LayoutInflater inflater;
    private BillDAO billDAO;
    private DatabaseHelper databaseHelper;

    public BillAdapter(Activity context, List<Bill> arrayBill) {
        super();
        this.context = context;
        this.billList = arrayBill;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        databaseHelper = new DatabaseHelper(context);
        billDAO = new BillDAO(context);
    }

    @Override
    public int getCount() {
        if (billList == null) {
            return 0;
        }
        return billList.size();
    }

    @Override
    public Object getItem(int position) {
        return billList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public static class ViewHolder {
        ImageView img;
        TextView txtBillID;
        TextView txtBillDate;
        ImageView imgDelete;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        BillAdapter.ViewHolder holder;
        if (convertView == null) {
            holder = new BillAdapter.ViewHolder();
            convertView = inflater.inflate(R.layout.item_bill, null);
            holder.img = (ImageView) convertView.findViewById(R.id.imgAvatarBill);
            holder.txtBillID = (TextView) convertView.findViewById(R.id.tvBillID);
            holder.txtBillDate = (TextView) convertView.findViewById(R.id.tvBillDate);
            holder.imgDelete = (ImageView) convertView.findViewById(R.id.imgDeleteBill);
            holder.imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //B1: định nghĩa alertDialog
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    //B2: thiết lập thông tin
                    builder.setTitle("Thông báo");
                    builder.setMessage("Bạn có muốn xóa " + billList.get(position).getMaHoaDon() + " ?");
                    builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            billDAO.deleteBillByID(billList.get(position).getMaHoaDon());
                            billList.remove(position);
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
            holder = (BillAdapter.ViewHolder) convertView.getTag();
        Bill _entry = (Bill) billList.get(position);
        holder.img.setImageResource(R.drawable.ic_avatar_user);
        holder.txtBillID.setText(_entry.getMaHoaDon());
        holder.txtBillDate.setText((CharSequence) _entry.getNgayMua());
        return convertView;
    }
    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
    public void changeDataset(List<Bill> items){
        this.billList = items;
        notifyDataSetChanged();
    }
}
