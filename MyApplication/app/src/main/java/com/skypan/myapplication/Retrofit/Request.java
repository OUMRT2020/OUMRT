package com.skypan.myapplication.Retrofit;

import java.util.Date;

public class Request {
    private String event_id;
    private String user_id;
    private Date actual_time;
    private String actual_start_point;
    private String actual_end_point;
    private String extra_needed;

    public Request(String user_id, Date actual_time, String actual_start_point, String actual_end_point) {
        this.user_id = user_id;
        this.actual_time = actual_time;
        this.actual_start_point = actual_start_point;
        this.actual_end_point = actual_end_point;
    }

    public String getEvent_id() {
        return event_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public Date getActual_time() {
        return actual_time;
    }

    public String getActual_start_point() {
        return actual_start_point;
    }

    public String getActual_end_point() {
        return actual_end_point;
    }

    public String getExtra_needed() {
        return extra_needed;
    }

    public void setEvent_id(String event_id) {
        this.event_id = event_id;
    }

    public void setExtra_needed(String extra_needed) {
        this.extra_needed = extra_needed;
    }
}
