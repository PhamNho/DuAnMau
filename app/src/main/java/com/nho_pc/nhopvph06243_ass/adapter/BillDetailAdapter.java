package com.nho_pc.nhopvph06243_ass.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
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
        this.inflater =
                (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
    @SuppressLint("SetTextI18n")
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
            holder.txtThanhTien = (TextView)
                    convertView.findViewById(R.id.tvThanhTien);
            holder.imgDelete = (ImageView)convertView.findViewById(R.id.ivDelete);
            holder.imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    billDetailDAO.deleteHoaDonChiTietByID(String.valueOf(billDetailList.get(position).getbilldetailID()));
                    billDetailList.remove(position);
                    notifyDataSetChanged();
                }
            });
            convertView.setTag(holder);
        }
        else
            holder=(ViewHolder)convertView.getTag();
        BillDetail _entry = (BillDetail) billDetailList.get(position);
        holder.txtMaSach.setText("Mã sách: "+_entry.getSach().getMaSach());
        holder.txtSoLuong.setText("Số lượng: "+_entry.getSoLuongMua());
        holder.txtGiaBia.setText("Giá bìa: "+_entry.getSach().getGiaBia() +" vnd");
        holder.txtThanhTien.setText("Thành tiền:"+_entry.getSoLuongMua()*_entry.getSach().getGiaBia()+" vnd");
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
