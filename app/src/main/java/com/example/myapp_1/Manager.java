package com.example.myapp_1;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import DateBaseConnection.PostsDataBaseConnection;
import Intrfaces.SearchCaller;
import utils.Post;
import utils.SearchFields;
import utils.User;

public class Manager extends AppCompatActivity implements View.OnClickListener, SearchCaller {
    private User user;
    private TextView hello;
    private Button post, users;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);

        GetElements();
        SetButtonsListeners();

        // get the user data
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.user = (User) extras.getSerializable("us");
        }

        // set text to say hello to the user
        hello.setText("Hello " + user.getName());
    }

    /**
     * get all elements on the screen (Buttons, texts, ...) as objects of the class
     */
    private void GetElements() {
        hello = (TextView) findViewById(R.id.hello_text1);
        post = (Button) findViewById(R.id.post);
        users = (Button) findViewById(R.id.user);
    }

    /**
     * set all button listeners
     */
    private void SetButtonsListeners() {
        post.setOnClickListener(this);
        users.setOnClickListener(this);
    }

    @Override
    public void gotSearchResults(ArrayList<Post> posts) {
        Intent i = new Intent(Manager.this, Delete.class);
        i.putExtra("postsList", posts);
        startActivity(i);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.post:
                PostsDataBaseConnection.search(this, new HashMap<SearchFields, String>());
                break;
            case R.id.user:
                startActivity(new Intent(Manager.this, ManagerUsersList.class));
                break;
        }
    }


}