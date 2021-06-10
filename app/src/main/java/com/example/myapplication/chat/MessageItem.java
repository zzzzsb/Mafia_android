package com.example.myapplication.chat;

public class MessageItem {
    String sender;
    String content;

    public MessageItem(String sender, String content) {
        this.sender = sender;
        this.content = content;
    }

    public MessageItem() {

    }


    //Getter & Setter
    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
