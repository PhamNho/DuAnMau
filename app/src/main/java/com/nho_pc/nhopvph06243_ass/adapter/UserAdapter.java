package com.nho_pc.nhopvph06243_ass.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nho_pc.nhopvph06243_ass.R;
import com.nho_pc.nhopvph06243_ass.dao.UserDAO;
import com.nho_pc.nhopvph06243_ass.listener.OnDelete;
import com.nho_pc.nhopvph06243_ass.listener.OnEdit;
import com.nho_pc.nhopvph06243_ass.model.Users;
import com.nho_pc.nhopvph06243_ass.ui.EditUserActivity;
import com.nho_pc.nhopvph06243_ass.ui.LoginActivity;
import com.nho_pc.nhopvph06243_ass.ui.UserActivity;

import java.util.List;

//public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
//    private List<Users> usersList;
//    private OnEdit onEdit;
//    private OnDelete onDelete;
//
//    public UserAdapter(List<Users> usersList, OnEdit onEdit, OnDelete onDelete) {
//        this.usersList = usersList;
//        this.onEdit = onEdit;
//        this.onDelete = onDelete;
//    }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
//        View  view=inflater.inflate(R.layout.item_user,parent,false);
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        Users users = usersList.get(position);
//        holder.tvName.setText(users.getName());
//        holder.tvPhoneNumber.setText(users.getPhoneNumber());
//        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onDelete.OnDelete();
//            }
//        });
//        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onEdit.OnEdit();
//            }
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        return usersList.size();
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder{
//        public ImageView imgAvatar,imgEdit,imgDelete;
//        public TextView tvName;
//        public TextView tvPhoneNumber;
//        public ViewHolder(View itemView) {
//            super(itemView);
//            imgAvatar = itemView.findViewById(R.id.imgAvatarUser);
//            tvPhoneNumber = itemView.findViewById(R.id.tvPhoneNumber);
//            tvName = itemView.findViewById(R.id.tvName);
//            imgEdit = itemView.findViewById(R.id.imgEditUser);
//            imgDelete = itemView.findViewById(R.id.imgDeleteUser);
//        }
//    }
//}

public class UserAdapter extends BaseAdapter {

    private List<Users> usersList;
    public Activity context;
    public LayoutInflater inflater;
    private UserDAO userDAO;

    public UserAdapter(Activity context, List<Users> arrayUser) {
        super();
        this.context = context;
        this.usersList = arrayUser;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        userDAO = new UserDAO(context);
    }

    @Override
    public int getCount() {
        if (usersList == null) {
            return 0;
        }
        return usersList.size();
    }

    @Override
    public Object getItem(int position) {
        return usersList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public static class ViewHolder {
        ImageView img;
        TextView txtName;
        TextView txtPhone;
        ImageView imgDelete;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_user, null);
            holder.img = (ImageView) convertView.findViewById(R.id.imgAvatarUser);
            holder.txtName = (TextView) convertView.findViewById(R.id.tvName);
            holder.txtPhone = (TextView) convertView.findViewById(R.id.tvPhoneNumber);
            holder.imgDelete = (ImageView) convertView.findViewById(R.id.imgDeleteUser);
            holder.imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //B1: định nghĩa alertDialog
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    //B2: thiết lập thông tin
                    builder.setTitle("Thông báo");
                    builder.setMessage("Bạn có muốn xóa " + usersList.get(position).getName() + " ?");
                    builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            userDAO.deleteUsersByID(usersList.get(position).getUserName());
                            usersList.remove(position);
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
//            holder.imgEdit.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    v.getContext().startActivity(new Intent(context, EditUserActivity.class));
//                }
//            });
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();
        Users _entry = (Users) usersList.get(position);
        holder.img.setImageResource(R.drawable.ic_avatar_user);
        holder.txtName.setText(_entry.getName());
        holder.txtPhone.setText(_entry.getPhoneNumber());
        return convertView;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    public void changeDataset(List<Users> items) {
        this.usersList = items;
        notifyDataSetChanged();
    }
}