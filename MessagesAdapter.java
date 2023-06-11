package com.example.whistile.messages;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.collection.CircularArray;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whistile.R;
import com.example.whistile.chat.Chat;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MyViewHolder> {

    private  List<MessageList> messageLists;
    private final Context context;

    public MessagesAdapter(List<MessageList> messageLists, Context context) {
        this.messageLists = messageLists;
        this.context = context;
    }

    public MessagesAdapter(List<com.example.whistile.messages.MessageList> messageLists, com.example.whistile.pagechat context) {
    }

    @NonNull
    @Override
    public MessagesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.messages_adapter_layout, null));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        MessageList list2 = messageLists.get(position);

        if (!list2.getProfilePic().isEmpty()){
            CircularArray<Object> picasso = null;
            int i = 0;
        }
        holder.name.setText(list2.getName());
        holder.lastMessage.setText(list2.getLastMessage());

        if (list2.getUnseenMessages() == 0){
            holder.unseeMessage.setVisibility(View.GONE);
            holder.lastMessage.setTextColor(Color.parseColor("#959595"));
        }
        else {
            holder.unseeMessage.setVisibility(View.VISIBLE);
            holder.unseeMessage.setText(list2.getUnseenMessages()+"");
            holder.lastMessage.setTextColor(context.getResources().getColor(R.color.teal_200));
        }
        holder.rootLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Chat.class);
                intent.putExtra("mobile", list2.getMobile());
                intent.putExtra("name", list2.getName());
                intent.putExtra("profile_pic", list2.getProfilePic());
                intent.putExtra("chat_key", list2.getChatKey());

                context.startActivity(intent);
            }
        });
    }

    public  void updataData(List<MessageList> messageLists){
        this.messageLists = messageLists;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return messageLists.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView profilePic;
        private TextView name;
        private TextView lastMessage;
        private TextView unseeMessage;

        private LinearLayout rootLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            profilePic = itemView.findViewById(R.id.profilePic);
            name = itemView.findViewById(R.id.name);
            lastMessage = itemView.findViewById(R.id.lastMessage);
            unseeMessage = itemView.findViewById(R.id.unseenMessages);
            rootLayout = itemView.findViewById(R.id.rootLayout);
        }
    }
}