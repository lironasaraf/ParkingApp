package com.example.myapp_1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import DateBaseConnection.UsersDataBaseConnection;
import Intrfaces.UsersGetter;
import utils.User;

public class ManagerUsersList extends AppCompatActivity implements UsersGetter {

    RecyclerView recyclerView;
    DatabaseReference database;
    UsersInManagerList myAdapter;
    ArrayList<User> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userlist);

        recyclerView = findViewById(R.id.userlist);
        database = FirebaseDatabase.getInstance().getReference("Users");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        myAdapter = new UsersInManagerList(this,list);
        recyclerView.setAdapter(myAdapter);

        UsersDataBaseConnection.getAllUsers(this);
    }

    @Override
    public void gotUsers(List<User> users) {
        this.list.addAll(users);
        myAdapter.notifyDataSetChanged();
    }
}