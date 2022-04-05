package com.example.myapp_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import DateBaseConnection.UsersDataBaseConnection;
import Intrfaces.PasswordReseter;
import utils.InputChecks;

public class ForgotPassword extends AppCompatActivity implements PasswordReseter {

    private EditText Email;
    private Button Reset;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        GetElements();
        SetButtonsListeners();
    }

    /**
     * get all elements on the screen (Buttons, texts, ...) as objects of the class
     */
    private void GetElements(){
        Email = (EditText) findViewById(R.id._email);
        Reset = (Button) findViewById(R.id._verify);
    }

    /**
     * set all button listeners
     */
    private void SetButtonsListeners() {
        Reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset_password();
            }
        });
    }

    /**
     * send email to reset the password
     */
    public void reset_password(){

        // input check
        String email = Email.getText().toString().trim();
        if(!InputChecks.CheckValidEMail(email)){
            Email.setError("not a valid email!");
            Email.requestFocus();
            return;
        }

        // reset
        UsersDataBaseConnection.passwordReset(this, email);

    }

    @Override
    public void onResetResults(boolean result) {
        if(result){
            Toast.makeText(ForgotPassword.this, "check your email to reset password", Toast.LENGTH_LONG).show();
            startActivity(new Intent(ForgotPassword.this, MainActivity.class));
        }
        else {
            Toast.makeText(ForgotPassword.this, "Try Again!", Toast.LENGTH_LONG).show();
        }
    }
}