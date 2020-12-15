package com.skypan.myapplication.Retrofit;

public class Auth {
    private String user_id;
    private String password;
    private String mail;

    public Auth(String password, String mail) {
        this.password = password;
        this.mail = mail;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getPassword() {
        return password;
    }

    public String getMail() {
        return mail;
    }
}
