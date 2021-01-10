package com.skypan.myapplication.Retrofit;

public class Custom_register {
    private User user;
    private Auth auth;

    public Custom_register(User user, Auth auth) {
        this.user = user;
        this.auth = auth;
    }

    public User getUser() {
        return user;
    }

    public Auth getAuth() {
        return auth;
    }
}
