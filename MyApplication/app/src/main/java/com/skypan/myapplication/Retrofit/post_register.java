package com.skypan.myapplication.Retrofit;

import com.google.gson.annotations.SerializedName;

public class post_register {

    //the text is email
    @SerializedName("body")
    private String text;

    public String getText() {
        return text;
    }
}
