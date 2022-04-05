package com.example.myapp_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import DateBaseConnection.UsersDataBaseConnection;
import Intrfaces.LoginCaller;
import utils.InputChecks;
import utils.User;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, LoginCaller {
    private Button Register, Login, Forgot_Password;
    private EditText email, password;
    private CheckBox manager_acc;
    private ProgressBar progressbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.GetElements();
        this.SetButtonsListeners();

    }

    /**
     * get all elements on the screen (Buttons, texts, ...) as objects of the class
     */
    private void GetElements(){
        Register = (Button)findViewById(R.id.register);
        Forgot_Password = (Button)findViewById(R.id.forgotpassword);
        Login = (Button)findViewById(R.id.login);
        email = (EditText)findViewById(R.id.editEmailAddress);
        password = (EditText)findViewById(R.id.editPassword);
        progressbar = (ProgressBar)findViewById(R.id.progressbar);
        manager_acc = (CheckBox) findViewById(R.id.ManagerPermission);
    }

    /**
     * set all button listeners
     */
    private void SetButtonsListeners() {
        Register.setOnClickListener(this);
        Forgot_Password.setOnClickListener(this);
        Login.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.register:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.login:
                loginButtonFunc();
                break;
            case R.id.forgotpassword:
                startActivity(new Intent(this, ForgotPassword.class));
                break;
        }
    }

    /**
     * get email and password from screen, and try to log in
     */
    private void loginButtonFunc(){

        progressbar.setVisibility(View.VISIBLE);

        // get email and password from screen
        String str_email = email.getText().toString().trim();
        String str_password = password.getText().toString().trim();

//        // DEBUG
//        str_email = "lironi06@gmail.com";
//        str_password = "12345678";
//        // END DEBUG

        // check input
        if(!InputChecks.CheckValidEMail(str_email)){
            email.setError("Invalid Email!");
            email.requestFocus();
            this.progressbar.setVisibility(View.GONE);
            return;
        }
        if(!InputChecks.CheckValidPassword(str_password)){
            password.setError("Invalid Password!");
            password.requestFocus();
            this.progressbar.setVisibility(View.GONE);
            return;
        }

        // try to log in
        UsersDataBaseConnection.login(this, str_email, str_password, manager_acc.isChecked());
    }


    /**
     * finish login after validation
     * @param user - the user's data. null if error
     * @param type:
     *            0 - normal login
     *            1 - manager login
     *            2 - data base connection error / no such user
     *            3 - not validated email
     *            4 - no manager access
     */
    @Override
    public void finishLogin(User user, int type) {
        progressbar.setVisibility(View.GONE);
        switch (type){
            // normal user
            case 0:
                Intent i = new Intent(MainActivity.this, UserProfile.class);
                i.putExtra("user", user);
                startActivity(i);
                break;
            // manager
            case 1:
                Intent j = new Intent(MainActivity.this, Manager.class);
                j.putExtra("us", user);
                startActivity(j);
                break;
            // error
            case 2:
                Toast.makeText(MainActivity.this, "Please try Again!", Toast.LENGTH_LONG).show();
                break;
            // not validated email
            case 3:
                Toast.makeText(MainActivity.this, "please verify your email!", Toast.LENGTH_LONG).show();
                break;
            case 4:
            // no manager access
                Toast.makeText(MainActivity.this, "you don't have manager access!", Toast.LENGTH_LONG).show();
                break;
            default:
        }
    }

}