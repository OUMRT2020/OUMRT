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
    private Date actual_time;
    private String actual_start_point;
    private String actual_end_point;
    private String extra_needed;
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

    public void setEvent_id(String event_id) {
        this.event_id = event_id;
    }

    public String getEvent_name() {
        return event_name;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDriver_id() {
        return driver_id;
    }

    public void setDriver_id(String driver_id) {
        this.driver_id = driver_id;
    }

    public String getPassenger_id() {
        return passenger_id;
    }

    public void setPassenger_id(String passenger_id) {
        this.passenger_id = passenger_id;
    }

    public ArrayList<Date> getAcceptable_time_interval() {
        return acceptable_time_interval;
    }

    public void setAcceptable_time_interval(ArrayList<Date> acceptable_time_interval) {
        this.acceptable_time_interval = acceptable_time_interval;
    }

    public ArrayList<String> getAcceptable_start_point() {
        return acceptable_start_point;
    }

    public void setAcceptable_start_point(ArrayList<String> acceptable_start_point) {
        this.acceptable_start_point = acceptable_start_point;
    }

    public ArrayList<String> getAcceptable_end_point() {
        return acceptable_end_point;
    }

    public void setAcceptable_end_point(ArrayList<String> acceptable_end_point) {
        this.acceptable_end_point = acceptable_end_point;
    }

    public int getAcceptable_sex() {
        return acceptable_sex;
    }

    public void setAcceptable_sex(int acceptable_sex) {
        this.acceptable_sex = acceptable_sex;
    }

    public int getMax_weight() {
        return max_weight;
    }

    public void setMax_weight(int max_weight) {
        this.max_weight = max_weight;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isIs_self_helmet() {
        return is_self_helmet;
    }

    public void setIs_self_helmet(boolean is_self_helmet) {
        this.is_self_helmet = is_self_helmet;
    }

    public ArrayList<Boolean> getRepeat() {
        return repeat;
    }

    public void setRepeat(ArrayList<Boolean> repeat) {
        this.repeat = repeat;
    }

    public Date getActual_time() {
        return actual_time;
    }

    public void setActual_time(Date actual_time) {
        this.actual_time = actual_time;
    }

    public String getActual_start_point() {
        return actual_start_point;
    }

    public void setActual_start_point(String actual_start_point) {
        this.actual_start_point = actual_start_point;
    }

    public String getActual_end_point() {
        return actual_end_point;
    }

    public void setActual_end_point(String actual_end_point) {
        this.actual_end_point = actual_end_point;
    }

    public String getExtra_needed() {
        return extra_needed;
    }

    public void setExtra_needed(String extra_needed) {
        this.extra_needed = extra_needed;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
