package com.skypan.myapplication.Retrofit;

import java.net.URL;

public class User {
    private String user_id;
    private String token;
    private String name;
    private String phone_num;
    private boolean sex;
    private int weight;
    private URL picture_url;
    private Rate rate;

    public User() {
    }

    public User(String user_id, String token, String name, String phone_num, boolean sex, int weight, URL picture_url, Rate rate) {
        this.user_id = user_id;
        this.token = token;
        this.name = name;
        this.phone_num = phone_num;
        this.sex = sex;
        this.weight = weight;
        this.picture_url = picture_url;
        this.rate = rate;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone_num() {
        return phone_num;
    }

    public void setPhone_num(String phone_num) {
        this.phone_num = phone_num;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public URL getPicture_url() {
        return picture_url;
    }

    public void setPicture_url(URL picture_url) {
        this.picture_url = picture_url;
    }

    public Rate getRate() {
        return rate;
    }

    public void setRate(Rate rate) {
        this.rate = rate;
    }
}
