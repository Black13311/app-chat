package com.example.whistile.chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.R;
import com.example.whistile.MemoryData;
import com.example.whistile.R;
import com.example.whistile.Register;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {

    private List<com.example.whistile.chat.ChatList> chatLists;
    private final Context context;
    private String userMobile;

    public ChatAdapter(List<com.example.whistile.chat.ChatList> chatLists, Context context) {
        this.chatLists = chatLists;
        this.context = context;
        this.userMobile = MemoryData.getData((Register) context);
    }

    public ChatAdapter(List<ChatList> chatListList, com.example.whistile.chat.Chat context) {
    }

    @NonNull
    @Override
    public ChatAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_adapter_layout, null));
    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.MyViewHolder holder, int position) {

        com.example.whistile.chat.ChatList list2 = chatLists.get(position);

        if (list2.getMobile().equals(userMobile)){
            holder.myLayout.setVisibility(View.VISIBLE);
            holder.oppoLayout.setVisibility(View.GONE);

            holder.myMessage.setText(list2.getMessage());
            holder.myTime.setText(list2.getData()+" "+list2.getTime());
        }
        else {
            holder.myLayout.setVisibility(View.GONE);
            holder.oppoLayout.setVisibility(View.VISIBLE);

            holder.oppoMessage.setText(list2.getMessage());
            holder.oppoTime.setText(list2.getData()+" "+list2.getTime());

        }
    }

    @Override
    public int getItemCount() {return chatLists.size();}

    public void updataChatList(List<com.example.whistile.chat.ChatList> chatLists){
        this.chatLists = chatLists;

    }

    public void updateChatList(com.example.whistile.chat.ChatList chatList) {
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout oppoLayout, myLayout;
        private TextView oppoMessage, myMessage;
        private TextView oppoTime, myTime;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            oppoLayout = itemView.findViewById(R.id.oppoLayout);
            myLayout = itemView.findViewById(R.id.myLayout);
            oppoMessage = itemView.findViewById(R.id.oppoMessage);
            myMessage = itemView.findViewById(R.id.myMessage);
            oppoTime = itemView.findViewById(R.id.oppoMessageTime);
            myTime = itemView.findViewById(R.id.myMsgTime);
        }
    }
}