package com.example.myapp_1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

import utils.Post;

public class SearchResults extends AppCompatActivity implements View.OnClickListener {

    TextView city, street, houseNum, from, to, price, phoneNum;
    ImageView photo;
    Button next, prev, spam;

    ArrayList<Post> posts;
    ArrayList<Uri> photos;
    int currentPostIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        GetElements();
        SetButtonsListeners();

        // get the posts list
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
           this.posts = (ArrayList<Post>)(extras.getSerializable("postsList"));
           this.photos = (ArrayList<Uri>)(extras.getSerializable("photosList")) ;
        }

        // set view to the first post
        if(posts.size() != 0) {
            this.currentPostIndex = 0;
            nextPost();
        }
        else{
            startActivity(new Intent(SearchResults.this, NoResults.class));
        }

    }


    /**
     * get all elements on the screen (Buttons, texts, ...) as objects of the class
     */
    private void GetElements(){
        this.city = findViewById(R.id.locationCity);
        this.street = findViewById(R.id.locationStreet);
        this.houseNum = findViewById(R.id.locationHouseNum);
        this.from = findViewById(R.id.From);
        this.to = findViewById(R.id.To);
        this.price = findViewById(R.id.Price);
        this.phoneNum = findViewById(R.id.phoneNum);

        this.photo = findViewById(R.id.Photo);

        this.next = findViewById(R.id.Next);
        this.prev = findViewById(R.id.Prev);
        this.spam = findViewById(R.id.reportspam);
    }


    /**
     * set all button listeners
     */
    private void SetButtonsListeners(){
        next.setOnClickListener(this);
        prev.setOnClickListener(this);
        spam.setOnClickListener(this);
    }

    /**
     *  set the screen to the next post
     */
    private void nextPost(){
        if(currentPostIndex == posts.size()){return;}

        Post currentPost = posts.get(currentPostIndex);
        setViewToPost(currentPost);
        currentPostIndex++;
    }
    /**
     *  set the screen to the previous post
     */
    private void prevPost(){
        if(currentPostIndex <= 1){return;}

        currentPostIndex--;

        Post currentPost = posts.get(currentPostIndex - 1);
        setViewToPost(currentPost);
    }
    /**
     *  send manger mail for post spam
     */
    private void spamRep(){

        final Dialog dialog = new Dialog(SearchResults.this);
        dialog.setContentView(R.layout.activity_report_reason_dialog);

        // adapt dialog window to screen size
        int width = (int)(getResources().getDisplayMetrics().widthPixels*0.90);
        int height = (int)(getResources().getDisplayMetrics().heightPixels*0.30);

        dialog.getWindow().setLayout(width, height);
        final EditText report_sender = dialog.findViewById(R.id.reportreason);
        Button submitButton = dialog.findViewById(R.id.report);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rep_send = report_sender.getText().toString();

                // send the message
                        Thread sender = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
        GMailSender sender = new GMailSender("ParkingApp12345@gmail.com", "Parking123456789");
            sender.sendMail("EmailSender App",
                    "This post suspect as spam,\nHis Details:" +"\nCity: " + city.getText().toString() + "\nStreet: " + street.getText().toString() + "\nHouse Number: " + houseNum.getText().toString() + "\nPrice: " + price.getText().toString() + "\nPhone Number: " + phoneNum.getText().toString() +"\nUser Message:\n" + rep_send,
                    "ParkingApp12345@gmail.com",
                    "ParkingApp12345@gmail.com");
                    dialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();}
            }
        });
        sender.start();
                dialog.dismiss();
            }
        });

        dialog.show();
    }
    /**
     * set the text on the screen to the given Post
     * @param currentPost
     */
    private void setViewToPost(Post currentPost) {

        // clear photo until new photo is downloaded from data base
        photo.setImageResource(android.R.color.transparent);

        city.setText(currentPost.getCity());
        street.setText(currentPost.getStreet());
        houseNum.setText(currentPost.getHouseNum());
        from.setText(currentPost.getDataFrom() + "  :  " + currentPost.getTimeFrom());
        to.setText(currentPost.getDateTo() + "  :  " + currentPost.getTimeTo());
        price.setText("" + currentPost.getPrice());
        phoneNum.setText(currentPost.getUser().getPhone());

        // download photo
        final long ONE_MEGABYTE = 1024 * 1024;
        FirebaseStorage.getInstance().getReference("Photos/" + currentPost.getID()).getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    photo.setImageBitmap(bitmap);
                }
            });
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.Next:
                nextPost();
                break;
            case  R.id.Prev:
                prevPost();
                break;
            case  R.id.reportspam:
                spamRep();
                break;
        }

    }
}