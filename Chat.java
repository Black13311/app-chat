package com.example.whistile.chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.whistile.MemoryData;
import com.example.whistile.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class Chat extends AppCompatActivity {

    private final List<ChatList> chatListList = new ArrayList<>();
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://whistile-default-rtdb.firebaseio.com/");
    private String chatKey;
    private String getUserMobile = "";

    private RecyclerView chattingRecyclerView;

    private ChatAdapter chatAdapter;

    private boolean loadingFirstTime = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        final ImageView backBtn = findViewById(R.id.backBtn);
        final TextView nameTV = findViewById(R.id.name);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) final EditText messageEditText = findViewById(R.id.messageEditTxt);
        final CircleImageView profilePic = findViewById(R.id.profilePic);
        final ImageView sendBtn = findViewById(R.id.sendBtn);

        chattingRecyclerView = findViewById(R.id.chattingRecyclerView);

        // get data from message adapter class
        final String getName = getIntent().getStringExtra("name");
        final String getProfilePic = getIntent().getStringExtra("profile_pic");
        chatKey = getIntent().getStringExtra("chat_key");
        getUserMobile = getIntent().getStringExtra("mobile");

        // get user mobile from memory
        getUserMobile = MemoryData.getData(Chat.this);

        nameTV.setText(getName);
        Picasso.get().load(getProfilePic).into(profilePic);

        chattingRecyclerView.setHasFixedSize(true);
        chattingRecyclerView.setLayoutManager(new LinearLayoutManager(Chat.this));

        chatAdapter = new com.example.whistile.chat.ChatAdapter(chatListList, Chat.this);
        chattingRecyclerView.setAdapter(chatAdapter);


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (chatKey.isEmpty()) {

                    // generate chat key. by default chatKey is "1"
                    chatKey = "1";

                    if (snapshot.hasChild("chat")) {
                        chatKey = String.valueOf(snapshot.child("chat").getChildrenCount() + 1);
                    }
                }

                if (snapshot.hasChild("chat")) {

                    if (snapshot.child("chat").child(chatKey).hasChild("messages")) {
                        chatListList.clear();
                        for (DataSnapshot messagesSnapshot : snapshot.child("chat").child(chatKey).child("messages").getChildren()) {

                            SimpleDateFormat simpleDateFormat = null;
                            Date date = null;
                            SimpleDateFormat simpleTimeFormat = null;
                            String messageTimestamps = null;
                            if (messagesSnapshot.hasChild("msg") && messagesSnapshot.hasChild("mobile")) {

                                messageTimestamps = messagesSnapshot.getKey();
                                final String getMobile = messagesSnapshot.child("mobile").getValue(String.class);
                                final String getMsg = messagesSnapshot.child("msg").getValue(String.class);

                                Timestamp timestamp = new Timestamp(Long.parseLong(messageTimestamps));
                                date = new Date(timestamp.getTime());
                                simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                                simpleTimeFormat = new SimpleDateFormat("hh:mm aa", Locale.getDefault());
                            }

                            com.example.whistile.chat.ChatList chatList;
                            chatList = null;
                            chatList = new com.example.whistile.chat.ChatList(chatList.getSender(), chatList.getReceiver(), chatList.getMessage(), simpleDateFormat.format(date), simpleTimeFormat.format(date));
                            chatList.add(chatList);

                            if (loadingFirstTime || Long.parseLong(messageTimestamps) > Long.parseLong(MemoryData.getLastMsgTS(Chat.this, chatKey))) {
                                loadingFirstTime = false;
                                String currentTimestamp = null;
                                MemoryData.saveLastMsgTS(currentTimestamp, chatKey);

                                chatAdapter.updateChatList(chatList);
                                chattingRecyclerView.scrollToPosition(chatList.size() - 1);
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}