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
import com.nho_pc.nhopvph06243_ass.model.Users;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder>{
    private final List<Users> userList;
    private final OnDelete onDelete;
    private final OnEdit onEdit;

    public UserAdapter(List<Users> userList, OnDelete onDelete, OnEdit onEdit) {
        this.userList = userList;
        this.onDelete = onDelete;
        this.onEdit = onEdit;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_user,parent,false);
        return new ViewHolder(itemView);
    }

    @SuppressLint({"RecyclerView", "SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final Users user = userList.get(position);

        if(user.getName().length()>20){
            String name = "Tên : "+user.getName().substring(0,20)+"...";
            holder.tvName.setText(name);
        }else{
            holder.tvName.setText("Tên: "+user.getName());
        }
        if(user.getUserName().length()>20){
            String id = "Phone : "+user.getPhoneNumber().substring(0,20)+"...";
            holder.tvPhone.setText(id);
        }else{
            holder.tvPhone.setText("Phone : "+user.getPhoneNumber());
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
        if (userList == null) return 0;
        return userList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView imgEdit;
        final ImageView imgDelete;
        final TextView tvName;
        final TextView tvPhone;

        ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvUser_Name_Item_User);
            tvPhone = itemView.findViewById(R.id.tvPhone_Item_User);
            imgEdit = itemView.findViewById(R.id.imgEdit_Item_User);
            imgDelete = itemView.findViewById(R.id.imgDelete_Item_User);
        }

    }
}