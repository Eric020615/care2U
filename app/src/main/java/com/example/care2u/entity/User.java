package com.example.care2u.entity;

public class User {

    public String userid,username,email,phone_number;

    public User(){

    }

    public User(String username, String email, String phone_number, String userid){
        this.username = username;
        this.email = email;
        this.phone_number = phone_number;
        this.userid=userid;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public String getUserid() {
        return userid;
    }
}
