package com.example.whistile;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.app.R;
import com.example.app.profile;
import com.example.app.video;
import com.example.app.wallet;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class dashboard extends AppCompatActivity {

    BottomNavigationView nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        nav = findViewById(R.id.nav);

        nav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        Intent homeIntent = new Intent(dashboard.this, video.class);
                        startActivity(homeIntent);
                        return true;
                    case R.id.chat:
                        Intent chatIntent = new Intent(dashboard.this, com.example.whistile.pagechat.class);
                        startActivity(chatIntent);
                        return true;
                    case R.id.wallet:
                        Intent walletIntent = new Intent(dashboard.this, wallet.class);
                        startActivity(walletIntent);
                        return true;
                    case R.id.follow:
                        // Handle follow menu item
                        return true;
                    case R.id.profile:
                        Intent profileIntent = new Intent(dashboard.this, profile.class);
                        startActivity(profileIntent);
                        return true;
                    default:
                        return false;
                }
            }
        });
    }
}