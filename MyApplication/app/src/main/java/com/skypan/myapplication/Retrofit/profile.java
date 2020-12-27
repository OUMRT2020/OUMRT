package com.skypan.myapplication.Retrofit;

public class profile {
    private String name;
    private String phone;
    private Rate rate;
    private Boolean sex;
    private int weight;

    public profile(String name, String phone, Rate rate, Boolean sex, int weight){
        this.name = name;
        this.phone = phone;
        this.rate = rate;
        this.sex = sex;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public Rate getRate() {
        return rate;
    }

    public Boolean getSex() {
        return sex;
    }

    public int getWeight() {
        return weight;
    }
}
