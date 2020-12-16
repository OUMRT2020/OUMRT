package com.skypan.myapplication.Retrofit;

import java.net.URL;

public class User {
    private String user_id;
    private String token;
    private String name;
    private String phone_num;
    private boolean sex;
    private int weight;
    private String picture_url;
    private Rate rate;

    public User(String token, String name, String phone_num, boolean sex, int weight) {
        this.token = token;
        this.name = name;
        this.phone_num = phone_num;
        this.sex = sex;
        this.weight = weight;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getToken() {
        return token;
    }

    public String getName() {
        return name;
    }

    public String getPhone_num() {
        return phone_num;
    }

    public boolean isSex() {
        return sex;
    }

    public int getWeight() {
        return weight;
    }

    public String getPicture_url() {
        return picture_url;
    }

    public Rate getRate() {
        return rate;
    }
}
