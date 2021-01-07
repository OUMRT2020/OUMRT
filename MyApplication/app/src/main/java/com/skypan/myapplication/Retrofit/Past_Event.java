package com.skypan.myapplication.Retrofit;

import java.util.ArrayList;

public class Past_Event {
    private String event_id;
    private String event_name;
    private String status;
    private String driver_id;
    private String passenger_id;
    private ArrayList<String> acceptable_time_interval;
    private ArrayList<String> acceptable_start_point;
    private ArrayList<String> acceptable_end_point;
    private int acceptable_sex;
    private int max_weight;
    private int price;
    private boolean is_self_helmet;
    private ArrayList<Boolean> repeat;

    private ArrayList<Request> all_request;
    private ArrayList<User> all_request_user;
    private Request my_request;

    private int is_rated;
    private String reason;
    private Request final_request;
    private User user;
}
