package com.example.whistile.chat;

public class ChatList {

    private String mobile, name, message, data, time;
    private String receiver;

    public ChatList(String mobile, String name, String message, String data, String time) {
        this.mobile = mobile;
        this.name = name;
        this.message = message;
        this.data = data;
        this.time = time;
    }

    public String getMobile() {
        return mobile;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }

    public String getData() {
        return data;
    }

    public String getTime() {
        return time;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getSender() {
        return mobile;
    }

    public int size() {
        // This method needs to be implemented
        return 0;
    }

    public void add(ChatList chatList) {
        // This method needs to be implemented
    }
}