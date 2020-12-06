package com.skypan.myapplication.passenger_model;

import java.net.URL;

public class user {
    String user_id,user_name, phone_num;
    boolean sex;
    int weight;
    URL picture_url;
    float rate;

    public user(String user_id, String user_name, String phone_num, boolean sex, int weight, URL picture_url, float rate) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.phone_num = phone_num;
        this.sex = sex;
        this.weight = weight;
        this.picture_url = picture_url;
        this.rate = rate;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getUser_name() {
        return user_name;
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

    public URL getPicture_url() {
        return picture_url;
    }

    public float getRate() {
        return rate;
    }
}
