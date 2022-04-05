package com.example.myapp_1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import utils.User;

public class UsersInManagerList extends RecyclerView.Adapter<UsersInManagerList.MyViewHolder1> {


    Context context;

    ArrayList<User> list;

    public UsersInManagerList(Context context, ArrayList<User> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return new MyViewHolder1(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder1 holder, int position) {
        User user = list.get(position);
        holder.firstName.setText(user.getName());
        holder.email.setText(user.getEmail());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class MyViewHolder1 extends RecyclerView.ViewHolder{

        TextView firstName, email;

        public MyViewHolder1(@NonNull View itemView) {
            super(itemView);

            firstName = itemView.findViewById(R.id.tvfirstName);
            email = itemView.findViewById(R.id.tvlastName);
        }
    }
}
