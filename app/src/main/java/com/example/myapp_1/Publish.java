package com.example.myapp_1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.IOException;

import DateBaseConnection.PostsDataBaseConnection;
import Intrfaces.PostUploader;
import utils.InputChecks;
import utils.Post;
import utils.User;


public class Publish extends AppCompatActivity implements View.OnClickListener, PostUploader {

    private TextView city, street, houseNum;
    private EditText price, dataFrom, timeFrom, dateTo, timeTo;
    private ImageView photo;
    private Switch weakly;
    private Button publish;
    private  User user;

    private Uri photoUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);

        GetElements();
        SetButtonsListeners();

        // get the user data
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.user = (User)extras.getSerializable("user");
        }

    }

    /**
     * get all elements on the screen (Buttons, texts, ...) as objects of the class
     */
    private void GetElements(){
        city = findViewById(R.id.city);
        street = findViewById(R.id.street);
        houseNum = findViewById(R.id.houseNumber);
        price = findViewById(R.id.price);
        dataFrom = findViewById(R.id.from);
        dateTo = findViewById(R.id.to);
        timeFrom = findViewById(R.id.timeFrom);
        timeTo = findViewById(R.id.timeTo);
        photo = findViewById(R.id.photo);
        weakly = findViewById(R.id.weakly);
        publish = findViewById(R.id.publishButton);
    }

    /**
     * set all button listeners
     */
    private void SetButtonsListeners() {
        publish.setOnClickListener(this);
        photo.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.publishButton:
                publishButtonFunctionality();
                break;
            case R.id.photo:
                pickPhoto();
                break;
        }
    }


    /**
     * allow the user to pick a photo from the gallery
     */
    private void pickPhoto(){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(gallery, 1);
    }

    /**
     * get the data from the screen and upload a post
     */
    private void publishButtonFunctionality(){

        //  get values from screen
        String str_city = city.getText().toString().trim(),
                str_street = street.getText().toString().trim(),
                str_houseNum = houseNum.getText().toString().trim(),
                str_dateFrom = dataFrom.getText().toString().trim(),
                str_dateTo = dateTo.getText().toString().trim(),
                str_timeFrom = timeFrom.getText().toString().trim(),
                str_timeTo = timeTo.getText().toString().trim();
        double num_price = Double.parseDouble(price.getText().toString().trim());

        boolean isWeakly = weakly.isChecked();

        // input check
        if(!InputChecks.isValidData(str_dateFrom)){
            dataFrom.setError("not a valid data!");
            dataFrom.requestFocus();
            return;
        }
        if(!InputChecks.isValidData(str_dateTo)){
            dateTo.setError("not a valid data!");
            dateTo.requestFocus();
            return;
        }
        if(!InputChecks.isValidTime(str_timeFrom)){
            timeFrom.setError("not a valid time of day!");
            timeFrom.requestFocus();
            return;
        }
        if(!InputChecks.isValidTime(str_timeTo)){
            timeTo.setError("not a valid time of day!");
            timeTo.requestFocus();
            return;
        }


        // create post
        Post post = new Post(str_city, str_street, str_houseNum, num_price,
                str_dateFrom, str_timeFrom, str_dateTo, str_timeTo,
                isWeakly, user);

        // upload post
        PostsDataBaseConnection.uploadPost(this, post, this.photoUri);

        // TODO: add option for weekly upload

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK && data != null){
            Uri selectedPhoto = data.getData();
            photo.setImageURI(selectedPhoto);
            this.photoUri = selectedPhoto;
        }

    }

    @Override
    public void uploaded(boolean isSuccessful) {
        if (isSuccessful){
            Toast.makeText(Publish.this, "post uploaded", Toast.LENGTH_LONG).show();

            // back to user profile
            Intent i = new Intent(Publish.this, UserProfile.class);
            i.putExtra("user", user);
            startActivity(i);
        }
        else {
            Toast.makeText(Publish.this, "could not upload post", Toast.LENGTH_LONG).show();
        }
    }
}



