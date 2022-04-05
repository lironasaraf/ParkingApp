package com.example.myapp_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import DateBaseConnection.PostsDataBaseConnection;
import Intrfaces.SearchCaller;
import utils.InputChecks;
import utils.Post;
import utils.SearchFields;

public class Search extends AppCompatActivity implements SearchCaller, View.OnClickListener {

    EditText city, street, from, to, maxPrice;
    Button search;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        GetElements();
        SetButtonsListeners();
    }



    /**
     * get all elements on the screen (Buttons, texts, ...) as objects of the class
     */
    private void GetElements(){

        this.city = findViewById(R.id.cityF);
        this.street = findViewById(R.id.streetF);
        this.from = findViewById(R.id.dateFromF);
        this.to = findViewById(R.id.datetoF);
        this.maxPrice = findViewById(R.id.maxPriceF);
        this.search = findViewById(R.id.searchButtonF);

    }

    /**
     * set all button listeners
     */
    private void SetButtonsListeners(){
        this.search.setOnClickListener(this);
    }

    /**
     * get search results for data base, and move to the display activity
     * @param posts  - search results from data base
     */
    @Override
    public void gotSearchResults(ArrayList<Post> posts) {
        Intent i = new Intent(Search.this, SearchResults.class);
        i.putExtra("postsList", posts);
        startActivity(i);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.searchButtonF){
            this.searchButtonFunctionality();
        }
    }

    /**
     * get data from screen and search for posts
     */
    private void searchButtonFunctionality(){
        String[] data = getDataFromScreen();    // get data for screen

        // input checks
        if(!data[2].equals("") && !InputChecks.isValidData(data[2])){
            this.from.setError("not a valid data!");
            this.from.requestFocus();
            return;
        }
        if(!data[3].equals("") && !InputChecks.isValidData(data[3])){
            this.to.setError("not a valid data!");
            this.to.requestFocus();
            return;
        }

        // put search values in a map
        Map<SearchFields, String> searchFor = new HashMap<>();
        if(!data[0].equals("")){searchFor.put(SearchFields.CITY, data[0]);}
        if(!data[1].equals("")){searchFor.put(SearchFields.STREET, data[1]);}
        if(!data[2].equals("")){searchFor.put(SearchFields.DATEFROM, data[2]);}
        if(!data[3].equals("")){searchFor.put(SearchFields.DATETO, data[3]);}
        if(!data[4].equals("")){searchFor.put(SearchFields.MAXPRICSE, data[4]);}

        // search
        PostsDataBaseConnection.search(this, searchFor);
    }

    /**
     * get all data from the screen
     * @return
     */
    private String[] getDataFromScreen(){

        String[] data = new String[5];

        data[0] = this.city.getText().toString().trim();
        data[1] = this.street.getText().toString().trim();
        data[2] = this.from.getText().toString().trim();
        data[3] = this.to.getText().toString().trim();
        data[4] = this.maxPrice.getText().toString().trim();

        return data;
    }

}