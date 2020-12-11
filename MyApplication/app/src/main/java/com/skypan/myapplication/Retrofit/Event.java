package com.skypan.myapplication.Retrofit;

import java.util.ArrayList;
import java.util.Date;

public class Event {
    private String event_id;
    private String event_name;
    private String status;
    private String driver_id;
    private String passenger_id;
    private ArrayList<Date> acceptable_time_interval;
    private ArrayList<String> acceptable_start_point;
    private ArrayList<String> acceptable_end_point;
    private int acceptable_sex;
    private int max_weight;
    private int price;
    private boolean is_self_helmet;
    private ArrayList<Boolean> repeat;

    private ArrayList<Request> all_request;
    private ArrayList<User> all_request_user;

    private String reason;
    private Request final_request;
    private User user;

    public Event(String event_id, String event_name, String status, String driver_id, String passenger_id, ArrayList<Date> acceptable_time_interval, ArrayList<String> acceptable_start_point, ArrayList<String> acceptable_end_point, int acceptable_sex, int max_weight, int price, boolean is_self_helmet, ArrayList<Boolean> repeat, User user) {
        this.event_id = event_id;
        this.event_name = event_name;
        this.status = status;
        this.driver_id = driver_id;
        this.passenger_id = passenger_id;
        this.acceptable_time_interval = acceptable_time_interval;
        this.acceptable_start_point = acceptable_start_point;
        this.acceptable_end_point = acceptable_end_point;
        this.acceptable_sex = acceptable_sex;
        this.max_weight = max_weight;
        this.price = price;
        this.is_self_helmet = is_self_helmet;
        this.repeat = repeat;
        this.user = user;
    }

    public String getEvent_id() {
        return event_id;
    }

    public String getEvent_name() {
        return event_name;
    }

    public String getStatus() {
        return status;
    }

    public String getDriver_id() {
        return driver_id;
    }

    public String getPassenger_id() {
        return passenger_id;
    }

    public ArrayList<Date> getAcceptable_time_interval() {
        return acceptable_time_interval;
    }

    public ArrayList<String> getAcceptable_start_point() {
        return acceptable_start_point;
    }

    public ArrayList<String> getAcceptable_end_point() {
        return acceptable_end_point;
    }

    public int getAcceptable_sex() {
        return acceptable_sex;
    }

    public int getMax_weight() {
        return max_weight;
    }

    public int getPrice() {
        return price;
    }

    public boolean isIs_self_helmet() {
        return is_self_helmet;
    }

    public ArrayList<Boolean> getRepeat() {
        return repeat;
    }

    public ArrayList<Request> getAll_request() {
        return all_request;
    }

    public ArrayList<User> getAll_request_user() {
        return all_request_user;
    }

    public String getReason() {
        return reason;
    }

    public Request getFinal_request() {
        return final_request;
    }

    public User getUser() {
        return user;
    }
}
