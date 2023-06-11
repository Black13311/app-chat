package com.example.whistile;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.R;
import com.example.whistile.messages.MessageList;
import com.example.whistile.messages.MessagesAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class pagechat extends Activity {
    private final List<MessageList> messageLists = new ArrayList<>();

    private String mobile;
    private String emaill;
    private String name;


    private int unseenMessage = 0;
    private String lastMessage = "";
    private String chatKey = "";

    private boolean dataSet = false;

    private RecyclerView messageRecyclerView;

    private MessagesAdapter messagesAdapter;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://whistile-default-rtdb.firebaseio.com/");

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_pagechat);

        final CircleImageView userProfilePic = findViewById(R.id.userProfilePic);

        messageRecyclerView = findViewById(R.id.messagesRecyclerView);

        //get intent data from Register.class activity
        mobile = getIntent().getStringExtra("mobile");
        emaill = getIntent().getStringExtra("email");
        name = getIntent().getStringExtra("name");

        messageRecyclerView.setHasFixedSize(true);
        messageRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // set adapter to recyclerview
        MessagesAdapter messageAdpter = new MessagesAdapter(messageLists, pagechat.this);

        messageRecyclerView.setAdapter(messageAdpter);


        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        // get profile pic from firebase database
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                final String profilePicUrl = snapshot.child("users").child("mobile").child("profile_pic").getValue(String.class);

                if(!profilePicUrl.isEmpty()) {
                    // set profile pic to circle image view

                    Picasso.get().load(profilePicUrl).into(userProfilePic);
                }

                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { progressDialog.dismiss(); }
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                messageLists.clear();
                unseenMessage = 0;
                lastMessage = "";
                chatKey = "";


                for (DataSnapshot dataSnapshot : snapshot.child("users").getChildren()){

                    final String getMobile = dataSnapshot.getKey();

                    dataSet = false;

                    if (!getMobile.equals(mobile)){

                        final String getName = dataSnapshot.child("name").getValue(String.class);
                        final String getProfilePic = dataSnapshot.child("profile_pic").getValue(String.class);
                        final String[][][] lastMessage = {{{""}}};
                        final int[] unseenMessages = {0};


                        databaseReference.child("chat");
                        databaseReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                int getChatCounts = (int) snapshot.getChildrenCount();

                                if (getChatCounts > 0) {

                                    for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {

                                        final String getKey = dataSnapshot1.getKey();
                                        chatKey = getKey;
                                        if (dataSnapshot1.hasChild("user1") && dataSnapshot1.hasChild("user_2") && dataSnapshot1.hasChild("messages")) {
                                            final String getUserOne = dataSnapshot1.child("user_1").getValue(String.class);
                                            final String getUserTwo = dataSnapshot1.child("user_2").getValue(String.class);

                                            if (getUserOne.equals(getMobile) && getUserTwo.equals(mobile) || (getUserOne.equals(mobile) && getUserTwo.equals(getMobile))) {

                                                for (DataSnapshot chatDataSnapshot : dataSnapshot1.child("messages").getChildren()) {


                                                    final long getMessageKey = Long.parseLong(chatDataSnapshot.getKey());
                                                    final long getLastSeenMessage = com.example.whistile.MemoryData.getLastMsTS(pagechat.this, getKey);

                                                    chatDataSnapshot.child("msg");
                                                    if (getMessageKey > getLastSeenMessage) {
                                                        unseenMessages[0]++;
                                                    }
                                                }
                                            }
                                        }

                                    }
                                }

                                if (!dataSet){
                                    dataSet = true;
                                    MessageList messageList = new MessageList(getName, getMobile, lastMessage[0][0], getProfilePic, unseenMessages[0], chatKey);
                                    messageList.add(messageList);
                                    messageAdpter.updataData(messageLists);
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });


                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}