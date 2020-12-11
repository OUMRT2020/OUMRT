package com.skypan.myapplication.Retrofit;

import java.util.Date;

public class Request {
    private String event_id;
    private String user_id;
    private Date actual_time;
    private String actual_start_point;
    private String actual_end_point;
    private String extra_needed;

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
}
