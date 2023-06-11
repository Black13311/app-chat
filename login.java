package com.example.whistile;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.app.R;

public class login extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final EditText usernameET = findViewById(R.id.usernameFT);
        @SuppressLint({"WrongViewCast", "MissingInflatedId", "LocalSuppress"}) final EditText passwordET = findViewById(R.id.passwordET);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) final ImageView passwordIcon = findViewById(R.id.passwordIcon);
        final TextView sigUpBtn = findViewById(R.id.signuUpBtr);

        passwordIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        sigUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(login.this, Register.class));
            }
        });

    }
}