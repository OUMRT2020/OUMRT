package com.skypan.myapplication.Retrofit;

import java.util.ArrayList;

public class Past_Event {
    private String event_id;
    private String event_name;
    private String status;
    private String driver_id;
    private String passenger_id;
    private ArrayList<String> acceptable_time_interval;

    public void setEvent_id(String event_id) {
        this.event_id = event_id;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDriver_id(String driver_id) {
        this.driver_id = driver_id;
    }

    public void setPassenger_id(String passenger_id) {
        this.passenger_id = passenger_id;
    }

    public void setAcceptable_time_interval(ArrayList<String> acceptable_time_interval) {
        this.acceptable_time_interval = acceptable_time_interval;
    }

    public void setAcceptable_start_point(ArrayList<String> acceptable_start_point) {
        this.acceptable_start_point = acceptable_start_point;
    }

    public void setAcceptable_end_point(ArrayList<String> acceptable_end_point) {
        this.acceptable_end_point = acceptable_end_point;
    }

    public void setAcceptable_sex(int acceptable_sex) {
        this.acceptable_sex = acceptable_sex;
    }

    public void setMax_weight(int max_weight) {
        this.max_weight = max_weight;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setIs_self_helmet(boolean is_self_helmet) {
        this.is_self_helmet = is_self_helmet;
    }

    public void setRepeat(ArrayList<Boolean> repeat) {
        this.repeat = repeat;
    }

    public void setAll_request(ArrayList<Request> all_request) {
        this.all_request = all_request;
    }

    public void setAll_request_user(ArrayList<User> all_request_user) {
        this.all_request_user = all_request_user;
    }

    public void setMy_request(Request my_request) {
        this.my_request = my_request;
    }

    public void setIs_rated(int is_rated) {
        this.is_rated = is_rated;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setFinal_request(Request final_request) {
        this.final_request = final_request;
    }

    public void setUser(User user) {
        this.user = user;
    }

    private ArrayList<String> acceptable_start_point;
    private ArrayList<String> acceptable_end_point;
    private int acceptable_sex;
    private int max_weight;
    private int price;

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

    public ArrayList<String> getAcceptable_time_interval() {
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

    public Request getMy_request() {
        return my_request;
    }

    public int getIs_rated() {
        return is_rated;
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
