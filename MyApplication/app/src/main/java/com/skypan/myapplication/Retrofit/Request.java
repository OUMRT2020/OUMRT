package com.skypan.myapplication.Retrofit;

import java.util.Date;

public class Request {
    private String user_id;
    private String event_id;
    private String start;
    private String end;
    private Date time;
    private String extra_needed;

    public Request() {
    }

    public Request(String user_id, String event_id, String start, String end, Date time, String extra_needed) {
        this.user_id = user_id;
        this.event_id = event_id;
        this.start = start;
        this.end = end;
        this.time = time;
        this.extra_needed = extra_needed;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getEvent_id() {
        return event_id;
    }

    public void setEvent_id(String event_id) {
        this.event_id = event_id;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getExtra_needed() {
        return extra_needed;
    }

    public void setExtra_needed(String extra_needed) {
        this.extra_needed = extra_needed;
    }
}
