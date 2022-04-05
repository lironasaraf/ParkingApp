package com.example.myapp_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import DateBaseConnection.PostsDataBaseConnection;
import Intrfaces.SearchCaller;
import utils.Post;
import utils.SearchFields;
import utils.User;

public class UserProfile extends AppCompatActivity implements View.OnClickListener, SearchCaller {

    private User user;
    private CardView search, publish, delete, Logout;
    private EditText helloText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        Toolbar bar = findViewById(R.id.toolBar);
        setSupportActionBar(bar);

        // get the user data
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.user = (User)extras.getSerializable("user");
        }

        GetElements();
        SetButtonsListeners();

        // set text to say hello to the user
        helloText.setTypeface(null, Typeface.BOLD);
        helloText.setText("Hello " + user.getName());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.user_prifile_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.logOut:
                log_out();
                break;
            case R.id.contact:
                startActivity(new Intent(UserProfile.this, Contacts.class));
                break;
            case R.id.userData:
                userDataFunctionality();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * get all elements on the screen (Buttons, texts, ...) as objects of the class
     */
    private void GetElements(){
        this.helloText = (EditText) findViewById(R.id.hello_text);
        this.delete = (CardView) findViewById(R.id.cardDelete);
        this.search = (CardView) findViewById(R.id.cardSearch);
        this.publish = (CardView) findViewById(R.id.cardUploud);
        this.Logout = (CardView) findViewById(R.id.cardLogOut);
    }

    /**
     * set all button listeners
     */
    private void SetButtonsListeners(){
        this.search.setOnClickListener(this);
        this.publish.setOnClickListener(this);
        this.delete.setOnClickListener(this);
        this.Logout.setOnClickListener(this);
    }


    /**
     * click listener for buttons
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.cardSearch:
                SearchButtonFunctionality();
                break;
            case R.id.cardUploud:
                publishButtonFunctionality();
                break;

            case R.id.cardDelete:
                deleteButtonFunctionality();
                break;
            case R.id.cardLogOut:
                log_out();
                break;

        }
    }


    private void SearchButtonFunctionality(){
        startActivity(new Intent(UserProfile.this, Search.class));
    }
    private void publishButtonFunctionality(){
        Intent i = new Intent(UserProfile.this, Publish.class);
        i.putExtra("user", user);
        startActivity(i);
    }
    private void deleteButtonFunctionality(){
        Map<SearchFields, String> forSearch = Collections.singletonMap(SearchFields.EMAIL, user.getEmail());
        PostsDataBaseConnection.search(this, forSearch);
    }

    private void log_out(){
        Intent intent = new Intent(UserProfile.this, MainActivity.class);
        startActivity(intent);
        finish();  // This call is missing.
    }

    private void userDataFunctionality(){
        final Dialog dialog = new Dialog(UserProfile.this);
        dialog.setContentView(R.layout.activity_user_data);

        // adapt dialog window to screen size
        int width = (int)(getResources().getDisplayMetrics().widthPixels*0.8);
        int height = (int)(getResources().getDisplayMetrics().heightPixels*0.8);
        dialog.getWindow().setLayout(width, height);

        final TextView name = dialog.findViewById(R.id.dataName);
        final TextView email = dialog.findViewById(R.id.dataEmail);
        final TextView phone = dialog.findViewById(R.id.dataPhone);

        name.setText(user.getName());
        email.setText(user.getEmail());
        phone.setText(user.getPhone());

        final Button close = dialog.findViewById(R.id.dataClose);
        final Button resetPassword = dialog.findViewById(R.id.dataPasswordReset);

        dialog.show();

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserProfile.this, ForgotPassword.class));
            }
        });
    }

    @Override
    public void gotSearchResults(ArrayList<Post> posts) {
        Intent i = new Intent(UserProfile.this, Delete.class);
        i.putExtra("postsList", posts);
        startActivity(i);
    }
}