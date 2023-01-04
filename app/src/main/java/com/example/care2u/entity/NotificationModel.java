package com.example.care2u.entity;

public class NotificationModel {
    private String content,date;

    public NotificationModel(String content,String date) {
        this.content = content;
        this.date = date;
    }

    public NotificationModel(){
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
