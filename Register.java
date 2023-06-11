\package com.example.whistile;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.app.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Register extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://whistile-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText email = findViewById(R.id.emailET);
        final EditText mobile = findViewById(R.id.mobileET);
        final EditText password = findViewById(R.id.passwordET);
        final EditText conpassword = findViewById(R.id.conpasswordET);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) final ImageView passwordIcon = findViewById(R.id.passwordIcon);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) final ImageView conpasswordIcon = findViewById(R.id.conpasswordIcon);
        final AppCompatButton signUpBtn = findViewById(R.id.signuUpBtr);
        final TextView signInBtn = findViewById(R.id.signInBtr);

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        if (!com.example.whistile.MemoryData.getData(this).isEmpty()){

            Intent intent = new Intent(Register.this, dashboard.class);
            intent.putExtra("mobile", MemoryData.getData(this));
            intent.putExtra("name", "");
            intent.putExtra("email", "" );
            startActivity(intent);
            finish();

        }
        passwordIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String getMobileTxt = mobile.getText().toString();
                final String getEmailTxt = email.getText().toString();

                // opening OTP verification Activity along with mobile and email
                Intent intent = new Intent(Register.this, com.example.whistile.OTPVerification.class);
                intent.putExtra("mobile", getMobileTxt);
                intent.putExtra("email", getEmailTxt);

                startActivity(intent);
            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog.show();

                final String mobileTxt = mobile.getText().toString();
                final String emailTxt = email.getText().toString();
                final String passwordTxt = password.getText().toString();
                final String conpasswordTxt = conpassword.getText().toString();

                // Check if the fields are empty
                if (mobileTxt.isEmpty() || emailTxt.isEmpty() || passwordTxt.isEmpty() || conpasswordTxt.isEmpty()) {
                    Toast.makeText(Register.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }

                // Check if the mobile number already exists in the database
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.child("user").hasChild(mobileTxt)) {
                            Toast.makeText(Register.this, "Mobile number already exists", Toast.LENGTH_SHORT).show();

                        } else {
                            // Store the user's information in the database
                            DatabaseReference userRef = databaseReference.child("user").child(mobileTxt);
                            userRef.child("email").setValue(emailTxt);
                            userRef.child("password").setValue(passwordTxt);


                            com.example.whistile.MemoryData.saveData(mobileTxt, Register.this);

                            // save mobile to memory
                            String nameTxt;
                            nameTxt = null;

                            com.example.whistile.MemoryData.saveData(mobileTxt, Register.this);

                            Toast.makeText(Register.this, "Success", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(Register.this, com.example.whistile.OTPVerification.class);
                            intent.putExtra("mobile", mobileTxt);
                            intent.putExtra("email", emailTxt);
                            startActivity(intent);
                            finish();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(Register.this, "Failed to read data", Toast.LENGTH_SHORT).show();

                        Dialog processDialog = null;
                        processDialog.dismiss();

                    }
                });
            }
        });
    }
}