package com.example.whistile;

import static java.util.concurrent.TimeUnit.SECONDS;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.app.R;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class OTPVerification extenyds AppCompatActivity {

    EditText enterNumber;
    Button getOTPButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpverification);

        enterNumber = findViewById(R.id.input_mobile_number);
        getOTPButton = findViewById(R.id.buttongetotp1);
        ProgressBar progressBar = findViewById(R.id.progrssbar_sending_otp);

        getOTPButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!enterNumber.getText().toString().trim().isEmpty()) {
                    if ((enterNumber.getText().toString().trim()).length() == 10) {

                        progressBar.setVisibility(View.VISIBLE);
                        getOTPButton.setVisibility(View.INVISIBLE);

                        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                "+856" + enterNumber.getText().toString(),
                                60,
                                TimeUnit.SECONDS,
                                OTPVerification.this,
                                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                                    @Override
                                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                        progressBar.setVisibility(View.GONE);
                                        getOTPButton.setVisibility(View.VISIBLE);
                                    }

                                    @Override
                                    public void onVerificationFailed(@NonNull FirebaseException e) {
                                        progressBar.setVisibility(View.GONE);
                                        getOTPButton.setVisibility(View.VISIBLE);
                                        Toast.makeText(OTPVerification.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onCodeSent(@NonNull String backendOTP, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                        progressBar.setVisibility(View.GONE);
                                        getOTPButton.setVisibility(View.VISIBLE);
                                        Intent intent = new Intent(getApplicationContext(), recreiveotp.class);
                                        intent.putExtra("mobile", enterNumber.getText().toString());
                                        intent.putExtra("backendOTP", backendOTP);
                                        startActivity(intent);
                                    }
                                });

                    } else {
                        Toast.makeText(OTPVerification.this, "Please enter correct number", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(OTPVerification.this, "Enter Mobile number", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}