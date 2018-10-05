package com.nho_pc.nhopvph06243_ass.adapter;

import android.annotation.SuppressLint;
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
import com.nho_pc.nhopvph06243_ass.dao.BillDetailDAO;
import com.nho_pc.nhopvph06243_ass.model.BillDetail;

import java.util.List;

public class BillDetailAdapter extends BaseAdapter {
    List<BillDetail> billDetailList;
    public Activity context;
    public LayoutInflater inflater;
    BillDetailDAO billDetailDAO;
    public BillDetailAdapter(Activity context, List<BillDetail> arrayHoaDonChiTiet) {
        super();
        this.context = context;
        this.billDetailList = arrayHoaDonChiTiet;
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        billDetailDAO = new BillDetailDAO(context);
    }
    @Override
    public int getCount() {
        return billDetailList.size();
    }
    @Override
    public Object getItem(int position) {
        return billDetailList.get(position);
    }
    @Override
    public long getItemId(int position) {
        return 0;
    }
    public static class ViewHolder {
        TextView txtMaSach;
        TextView txtSoLuong;
        TextView txtGiaBia;
        TextView txtThanhTien;
        ImageView imgDelete;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null)
        {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_bill_detail, null);
            holder.txtMaSach = (TextView) convertView.findViewById(R.id.tvMaSach);
            holder.txtSoLuong = (TextView) convertView.findViewById(R.id.tvSoLuong);
            holder.txtGiaBia = (TextView) convertView.findViewById(R.id.tvGiaBia);
            holder.txtThanhTien = (TextView) convertView.findViewById(R.id.tvThanhTien);
            holder.imgDelete = (ImageView)convertView.findViewById(R.id.ivDelete);
            holder.imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //B1: định nghĩa alertDialog
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    //B2: thiết lập thông tin
                    builder.setTitle("Thông báo");
                    builder.setMessage("Bạn có muốn xóa " + billDetailList.get(position).getSach() + " ?");
                    builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            billDetailDAO.deleteHoaDonChiTietByID(billDetailList.get(position).getbilldetailID());
                            billDetailList.remove(position);
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
        }
        else
            holder=(ViewHolder)convertView.getTag();
        BillDetail _entry = (BillDetail) billDetailList.get(position);
        holder.txtMaSach.setText("Mã sách: "+_entry.getSach().getMaSach());
        holder.txtSoLuong.setText("Số lượng: "+_entry.getSoLuongMua());
        holder.txtGiaBia.setText("Giá bìa: "+_entry.getSach().getGiaBia() +" vnđ");
        holder.txtThanhTien.setText("Thành tiền:"+_entry.getSoLuongMua()*_entry.getSach().getGiaBia()+" vnđ");
        return convertView;
    }
    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
    public void changeDataset(List<BillDetail> items){
        this.billDetailList = items;
        notifyDataSetChanged();
    }
}
