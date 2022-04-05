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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

import DateBaseConnection.PostsDataBaseConnection;
import Intrfaces.PostRemover;
import utils.Post;

public class Delete extends AppCompatActivity implements View.OnClickListener, PostRemover {

    TextView city, street, houseNum, from, to, price, phoneNum;
    ImageView photo;
    Button next, prev, delete;

    ArrayList<Post> posts;
    int currentPostIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        GetElements();
        SetButtonsListeners();

        // get the posts list
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.posts = (ArrayList<Post>)(extras.getSerializable("postsList"));
        }

        // set view to the first post
        if(posts.size() != 0) {
            this.currentPostIndex = 0;
            nextPost();
        }
        else{
            startActivity(new Intent(Delete.this, NoResults.class));
            finish();
        }

    }


    /**
     * get all elements on the screen (Buttons, texts, ...) as objects of the class
     */
    private void GetElements(){
        this.city = findViewById(R.id.locationCityD);
        this.street = findViewById(R.id.locationStreetD);
        this.houseNum = findViewById(R.id.locationHouseNumD);
        this.from = findViewById(R.id.FromD);
        this.to = findViewById(R.id.ToD);
        this.price = findViewById(R.id.PriceD);
        this.phoneNum = findViewById(R.id.phoneNumD);

        this.photo = findViewById(R.id.PhotoD);

        this.next = (Button) findViewById(R.id.NextD);
        this.prev = (Button) findViewById(R.id.PrevD);
        this.delete = (Button) findViewById(R.id.DeleteD);
    }


    /**
     * set all button listeners
     */
    private void SetButtonsListeners(){

        this.next.setOnClickListener(this);
        this.prev.setOnClickListener(this);
        this.delete.setOnClickListener(this);
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
     * set the text on the screen to the given Post
     * @param currentPost
     */
    private void setViewToPost(Post currentPost){

        // clear photo until new photo is downloaded from data base
        photo.setImageResource(android.R.color.transparent);

        city.setText(currentPost.getCity());
        street.setText(currentPost.getStreet());
        houseNum.setText(currentPost.getHouseNum());
        from.setText(currentPost.getDataFrom() + "  :  " +currentPost.getTimeFrom());
        to.setText(currentPost.getDateTo() + "  :  " +currentPost.getTimeTo());
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

    /**
     * Delete the current displayed post
     */
    private void DeletePost(){
        Post postToDelete = this.posts.get(currentPostIndex - 1);

        // if removing the first post
        if(currentPostIndex == 1){
            // if removed last post
            if(this.posts.size() == 1){
                startActivity(new Intent(Delete.this , NoResults.class));
            }
            else {
                nextPost();
                this.posts.remove(postToDelete);
                currentPostIndex--;
            }
        }
        // if removing any other post
        else{
            currentPostIndex = 0;
            nextPost();
            this.posts.remove(postToDelete);
        }


        PostsDataBaseConnection.deletePost(this, postToDelete);

    }
    public void valid_delete(){
        final Dialog dialog = new Dialog(Delete.this);
        dialog.setContentView(R.layout.activity_validdelete);
        // adapt dialog window to screen size
        int width = (int)(getResources().getDisplayMetrics().widthPixels);
        int height = (int)(getResources().getDisplayMetrics().heightPixels*0.30);

        dialog.getWindow().setLayout(width, height);
        final TextView title_send = dialog.findViewById(R.id.check_valid);
        Button delete_anyway = dialog.findViewById(R.id.delete_anyway);
        Button cancel = dialog.findViewById(R.id.cancel);
        dialog.show();
        delete_anyway.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeletePost();
                dialog.dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
    @Override
    public void postRemoved(boolean isSuccessful) {
        if(!isSuccessful){
            Toast.makeText(Delete.this, "an error occurred!", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(Delete.this, "post successfully removed", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.NextD:
                nextPost();
                break;
            case  R.id.PrevD:
                prevPost();
                break;
            case R.id.DeleteD:
                valid_delete();
                break;
        }
    }

}