package com.example.whistile.messages;

public class MessageList {

    private final String chatkey;
    private String name, mobile, lastMessage, profilePic;

    private int unseenMessages;

    public MessageList(String name, String mobile, String[] lastMessage, String profilePic, int unseenMessages, String chatKey) {
        this.name = name;
        this.mobile = mobile;
        this.lastMessage = String.valueOf(lastMessage);
        this.profilePic = profilePic;
        this.unseenMessages = unseenMessages;
        this.chatkey = chatKey;
    }

    public String getName() {
        return name;
    }

    public String getMobile() {
        return mobile;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public int getUnseenMessages() {
        return unseenMessages;
    }

    public  String getChatKey() {
        String chatKey;
        return chatkey;
    }

    public void add(MessageList messageList) {
    }
}