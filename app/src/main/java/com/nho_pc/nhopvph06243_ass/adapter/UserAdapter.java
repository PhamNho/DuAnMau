package com.nho_pc.nhopvph06243_ass.adapter;

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

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private List<Users> usersList;
    private OnEdit onEdit;
    private OnDelete onDelete;

    public UserAdapter(List<Users> usersList, OnEdit onEdit, OnDelete onDelete) {
        this.usersList = usersList;
        this.onEdit = onEdit;
        this.onDelete = onDelete;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View  view=inflater.inflate(R.layout.item_user,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Users users = usersList.get(position);
        holder.tvName.setText(users.getName());
        holder.tvPhoneNumber.setText(users.getPhoneNumber());
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDelete.OnDelete();
            }
        });
        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEdit.OnEdit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView imgAvatar,imgEdit,imgDelete;
        public TextView tvName;
        public TextView tvPhoneNumber;
        public ViewHolder(View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.imgAvatarUser);
            tvPhoneNumber = itemView.findViewById(R.id.tvPhoneNumber);
            tvName = itemView.findViewById(R.id.tvName);
            imgEdit = itemView.findViewById(R.id.imgEditUser);
            imgDelete = itemView.findViewById(R.id.imgDeleteUser);
        }
    }
}
