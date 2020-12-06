package com.skypan.myapplication.passenger_model;

import java.util.Date;
import java.util.List;

public class event {
    //driver
    String event_id, event_name, status, driver_id, passenger_id;
    List<Date> acceptable_time_interval, acceptable_pt_start, acceptable_pt_end;
    int acceptable_sex, max_weight;
    boolean is_self_helmet;

    //passenger
    boolean[] repeat;
    Date actual_time;
    String actual_pt_start, actual_pt_end, extra_need;

    user user;

    public event() {
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

    public List<Date> getAcceptable_time_interval() {
        return acceptable_time_interval;
    }

    public void setAcceptable_time_interval(List<Date> acceptable_time_interval) {
        this.acceptable_time_interval = acceptable_time_interval;
    }

    public List<Date> getAcceptable_pt_start() {
        return acceptable_pt_start;
    }

    public void setAcceptable_pt_start(List<Date> acceptable_pt_start) {
        this.acceptable_pt_start = acceptable_pt_start;
    }

    public List<Date> getAcceptable_pt_end() {
        return acceptable_pt_end;
    }

    public void setAcceptable_pt_end(List<Date> acceptable_pt_end) {
        this.acceptable_pt_end = acceptable_pt_end;
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

    public boolean isIs_self_helmet() {
        return is_self_helmet;
    }

    public void setIs_self_helmet(boolean is_self_helmet) {
        this.is_self_helmet = is_self_helmet;
    }

    public boolean[] getRepeat() {
        return repeat;
    }

    public void setRepeat(boolean[] repeat) {
        this.repeat = repeat;
    }

    public Date getActual_time() {
        return actual_time;
    }

    public void setActual_time(Date actual_time) {
        this.actual_time = actual_time;
    }

    public String getActual_pt_start() {
        return actual_pt_start;
    }

    public void setActual_pt_start(String actual_pt_start) {
        this.actual_pt_start = actual_pt_start;
    }

    public String getActual_pt_end() {
        return actual_pt_end;
    }

    public void setActual_pt_end(String actual_pt_end) {
        this.actual_pt_end = actual_pt_end;
    }

    public String getExtra_need() {
        return extra_need;
    }

    public void setExtra_need(String extra_need) {
        this.extra_need = extra_need;
    }
}
