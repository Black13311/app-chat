package com.example.whistile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class recreiveotp extends AppCompatActivity {

    EditText inputnumber1, inputnumber2, inputnumber3, inputnumber4, inputnumber5, inputnumber6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recreiveotp);

        final Button verifybuttonclick = findViewById(R.id.buttungetotp);

        inputnumber1 = findViewById(R.id.inputotp1);
        inputnumber2 = findViewById(R.id.inputotp2);
        inputnumber3 = findViewById(R.id.inputotp3);
        inputnumber4 = findViewById(R.id.inputotp4);
        inputnumber5 = findViewById(R.id.inputotp5);
        inputnumber6 = findViewById(R.id.inputotp6);

        TextView textView = findViewById(R.id.textmobileshownumer);
        textView.setText(String.format(
                "+91-%s", getIntent().getShortArrayExtra("mobile")
        ));

        final Object[] getotpbackend = {getIntent().getExtras().getString("backendotp")};

        final ProgressBar progressBarverifyotp = findViewById(R.id.progress_verify_otp);
        numberotpmove();

        verifybuttonclick.setOnClickListener(v -> {
            if (!inputnumber1.getText().toString().trim().isEmpty() &&
                    !inputnumber2.getText().toString().trim().isEmpty() &&
                    !inputnumber3.getText().toString().trim().isEmpty() &&
                    !inputnumber4.getText().toString().trim().isEmpty() &&
                    !inputnumber5.getText().toString().trim().isEmpty() &&
                    !inputnumber6.getText().toString().trim().isEmpty()) {
                String entercodeotp = inputnumber1.getText().toString() +
                        inputnumber2.getText().toString() +
                        inputnumber3.getText().toString() +
                        inputnumber4.getText().toString() +
                        inputnumber5.getText().toString() +
                        inputnumber6.getText().toString();

                if (getotpbackend[0] != null) {
                    progressBarverifyotp.setVisibility(View.VISIBLE);
                    verifybuttonclick.setVisibility(View.INVISIBLE);

                    PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(
                            (String) getotpbackend[0], entercodeotp);
                    FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                            .addOnCompleteListener(task -> {
                                progressBarverifyotp.setVisibility(View.GONE);
                                verifybuttonclick.setVisibility(View.VISIBLE);

                                if (task.isSuccessful()) {
                                    Intent intent = new Intent(getApplicationContext(), com.example.whistile.dashboard.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(recreiveotp.this, "Enter correct OTP", Toast.LENGTH_SHORT).show();
                                }
                            });

                } else {
                    Toast.makeText(recreiveotp.this, "Please check internet connection", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(recreiveotp.this, "Please enter all numbers", Toast.LENGTH_SHORT).show();
            }
        });

        View resendlabel = null;
        resendlabel.setOnClickListener(v -> {
            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    "+856" + getIntent().getExtras().getString("mobile"),
                    60,
                    TimeUnit.SECONDS,
                    recreiveotp.this,
                    new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                        @Override
                        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        }

                        @Override
                        public void onVerificationFailed(@NonNull FirebaseException e) {
                            Toast.makeText(recreiveotp.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCodeSent(@NonNull String newbackendotp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                            super.onCodeSent(newbackendotp, forceResendingToken);
                            getotpbackend[0] = newbackendotp;
                            Toast.makeText(recreiveotp.this, "OTP sent successfully", Toast.LENGTH_SHORT).show();
                        }
                    }
            );
        });


        TextView resendLabel = findViewById(R.id.textmobileshownumer);

        resendLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implement the logic to resend the OTP here
            }
        });


        {

            inputnumber1.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (!s.toString().trim().isEmpty()) {
                        inputnumber2.requestFocus();
                    }

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            inputnumber2.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (!s.toString().trim().isEmpty()) {
                        inputnumber3.requestFocus();
                    }

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            inputnumber3.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (!s.toString().trim().isEmpty()) {
                        inputnumber4.requestFocus();
                    }

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            inputnumber4.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (!s.toString().trim().isEmpty()) {
                        inputnumber5.requestFocus();
                    }

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            inputnumber5.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (!s.toString().trim().isEmpty()) {
                        inputnumber6.requestFocus();
                    }

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }
    }

    private void numberotpmove() {

    }
}